package me.vukas.common.entity.operation;

import me.vukas.common.entity.EntityComparison;
import me.vukas.common.entity.EntityDefinition;
import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.Name;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.element.LeafElement;
import me.vukas.common.entity.element.NodeElement;
import me.vukas.common.entity.generation.array.ArrayEntityGeneration;
import me.vukas.common.entity.generation.map.MapEntryEntityGeneration;
import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.key.LeafKey;
import me.vukas.common.entity.key.NodeKey;

import java.lang.reflect.Field;
import java.util.*;

import static me.vukas.common.base.Objects.*;

public class Diff {
    private Compare compare;
    private final Stack<Object> visitedElements = new Stack<Object>();
    private final Stack<Object> visitedKeys = new Stack<Object>();
    private final Map<Class, EntityDefinition> typesToEntityDefinitions;
    private final List<EntityGeneration<?>> entityGenerations;

    @SuppressWarnings("unchecked")
    private Diff(Builder builder) {
        this.typesToEntityDefinitions = builder.typesToEntityDefinitions;
        this.entityGenerations = builder.entityGenerations;

        for (EntityGeneration entityGeneration : this.entityGenerations) {
            entityGeneration.setDiff(this);
        }

        this.compare = new Compare.Builder(new ArrayList<EntityDefinition>(this.typesToEntityDefinitions.values()),
                (List<EntityComparison<?>>) (List<?>) this.entityGenerations).build();
    }

    public <T> Element<Name, T> diff(T original, T revised) {
        Class originalClass = original == null ? null : original.getClass();
        Key<Name, T> rootKey = this.generateKey(Name.ROOT, originalClass, null, original);
        return this.diff(original, revised, Name.ROOT, originalClass, null, rootKey);
    }

    public <N, T> Element<N, T> diff(T original, T revised, N elementName, Class fieldType, Class containerType, Key<N, T> key) {
        if (original == revised) {
            return new LeafElement<N, T>(elementName, Element.Status.EQUAL, key, revised);
        }

        if (original == null || revised == null) {
            return new LeafElement<N, T>(elementName, Element.Status.MODIFIED, key, revised);
        }

        if (!getWrappedClass(fieldType).equals(revised.getClass())) {
            throw new UnsupportedOperationException("Diff objects must have the same type");
        }

        if (isStringOrPrimitiveOrWrapped(fieldType)) {
            if (original.equals(revised)) {
                return new LeafElement<N, T>(elementName, Element.Status.EQUAL, key, revised);
            }
            return new LeafElement<N, T>(elementName, Element.Status.MODIFIED, key, revised);
        }

        if (this.visitedElements.contains(original) || this.visitedKeys.contains(original)) {
            return new LeafElement<N, T>((N)Name.CIRCULAR_REFERENCE, Element.Status.EQUAL, null, null);    //TODO: should we return null or something else on circular reference
        }

        this.visitedElements.push(original);

        if (fieldType.isArray()  || Collection.class.isAssignableFrom(fieldType) || Map.class.isAssignableFrom(fieldType)) {
            EntityGeneration<T> entityGeneration = new ArrayEntityGeneration<T>(this, this.compare);
            Element<N, T> element = entityGeneration.diff(original, revised, elementName, fieldType, containerType, key);
            this.visitedElements.pop();
            return element;
        }

        for (EntityGeneration<?> entityGeneration : this.entityGenerations) { //TODO: class hierarchy priority
            if (entityGeneration.getType().isAssignableFrom(fieldType)) {
                EntityGeneration<T> entityGenerationCasted = (EntityGeneration<T>)entityGeneration;
                Element<N, T> element = entityGenerationCasted.diff(original, revised, elementName, fieldType, containerType, key);
                this.visitedElements.pop();
                return element;
            }
        }

        List<Element<?, ?>> elements = this.processFields(fieldType, original, revised);

        Element.Status status = determineElementStatus(elements);

        this.visitedElements.pop();
        return new NodeElement<N, T>(elementName, status, key, elements);
    }

    private <T> List<Element<?, ?>> processFields(Class fieldType, T original, T revised) {
        try {
            return this.processAllClassFields(fieldType, original, revised);
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    private <T> List<Element<?, ?>> processAllClassFields(Class fieldType, T original, T revised) throws IllegalAccessException {
        List<Element<?, ?>> elements = new ArrayList<Element<?, ?>>();
        List<Field> fields = getAllFields(fieldType);
        for (Field field : fields) {
            field.setAccessible(true);
            Object originalField = field.get(original);
            Class originalFieldType = originalField == null ? null : originalField.getClass();
            Key fieldKey = this.generateKey(field.getName(), field.getType(), field.getDeclaringClass(), originalField);
            Element element = this.diff(originalField, field.get(revised), field.getName(), originalFieldType, field.getDeclaringClass(), fieldKey);
            elements.add(element);
        }
        return elements;
    }

    public static Element.Status determineElementStatus(List<Element<?, ?>> children) {
        for (Element<?, ?> element : children) {
            if (element.getStatus() != Element.Status.EQUAL) {
                return Element.Status.MODIFIED;
            }
        }
        return Element.Status.EQUAL;
    }

    public <N, T> Key<N, T> generateKey(N elementName, Class elementType, Class containerType, T value) {
        if (value == null || isStringOrPrimitiveOrWrapped(elementType) || Enum.class.isAssignableFrom(elementType)) {
            return new LeafKey<N, T>(elementName, elementType, containerType, value);
        }

        if(this.visitedElements.contains(value) || this.visitedKeys.contains(value)){
            return new LeafKey<N, T>((N)Name.CIRCULAR_REFERENCE, elementType, containerType, null);    //TODO: should we return null or something else on circular reference; Can this even happen?
        }

        this.visitedKeys.push(value);

        if(elementType.isArray() || Collection.class.isAssignableFrom(elementType) || Map.class.isAssignableFrom(elementType)){
            EntityGeneration<T> entityGeneration = new ArrayEntityGeneration<T>(this, this.compare);
            Key<N, T> key = entityGeneration.generateKey(elementName, elementType, containerType, value);
            this.visitedKeys.pop();
            return key;
        }

        for(EntityGeneration<?> entityGeneration : this.entityGenerations){
            if(entityGeneration.getType().isAssignableFrom(elementType)){   //TODO: class hierarchy priority
                EntityGeneration<T> entityGenerationCasted = (EntityGeneration<T>)entityGeneration;
                Key<N, T> key = entityGenerationCasted.generateKey(elementName, elementType, containerType, value);
                this.visitedKeys.pop();
                return key;
            }
        }

        List<Key<?, ?>> keys = new ArrayList<Key<?, ?>>();
        List<Field> fields;

        if(this.typesToEntityDefinitions.containsKey(elementType)){
            fields = this.typesToEntityDefinitions.get(elementType).getFields();
        }
        else{
            fields = getAllFields(elementType);
        }

        for(Field field : fields){
            try{
                field.setAccessible(true);
                Key<?, ?> key = this.generateKey(field.getName(), field.getType(), field.getDeclaringClass(), field.get(value));
                keys.add(key);
            }
            catch (IllegalAccessException e){
                //TODO: remove this catch to separate method
            }
        }

        this.visitedKeys.pop();
        return new NodeKey<N, T>(elementName, elementType, containerType, keys);
    }

    public static class Builder {
        private final Map<Class, EntityDefinition> typesToEntityDefinitions = new HashMap<Class, EntityDefinition>();
        private final List<EntityGeneration<?>> entityGenerations = new ArrayList<EntityGeneration<?>>();

        public Builder() {
            this.registerInternalEntityGenerations();
        }

        public Builder(List<EntityDefinition> entityDefinitions, List<EntityGeneration<?>> entityGenerations) {
            this();
            for (EntityDefinition entityDefinition : entityDefinitions) {
                this.typesToEntityDefinitions.putIfAbsent(entityDefinition.getType(), entityDefinition);
            }
            this.entityGenerations.addAll(entityGenerations);
        }

        public Builder registerEntity(EntityDefinition entityDefinition) {
            this.typesToEntityDefinitions.putIfAbsent(entityDefinition.getType(), entityDefinition);
            return this;
        }

        public Builder registerEntities(List<EntityDefinition> entityDefinitions) {
            for (EntityDefinition entityDefinition : entityDefinitions) {
                this.typesToEntityDefinitions.putIfAbsent(entityDefinition.getType(), entityDefinition);
            }
            return this;
        }

        public Builder registerEntityGeneration(EntityGeneration entityGeneration) {
            this.entityGenerations.add(entityGeneration);
            return this;
        }

        public Builder registerEntityGenerations(List<EntityGeneration<?>> entityGenerations) {
            this.entityGenerations.addAll(entityGenerations);
            return this;
        }

        private void registerInternalEntityGenerations() {
            this.registerEntityGeneration(new MapEntryEntityGeneration());
        }

        public Diff build() {
            return new Diff(this);
        }
    }
}