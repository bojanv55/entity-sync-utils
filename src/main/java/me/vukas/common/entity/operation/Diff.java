package me.vukas.common.entity.operation;

import me.vukas.common.entity.EntityComparison;
import me.vukas.common.entity.EntityDefinition;
import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.Name;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.element.LeafElement;
import me.vukas.common.entity.element.NodeElement;
import me.vukas.common.entity.generation.collection.CollectionEntityGeneration;
import me.vukas.common.entity.generation.map.MapEntityGeneration;
import me.vukas.common.entity.generation.map.MapEntryEntityGeneration;
import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.key.LeafKey;

import java.lang.reflect.Field;
import java.util.*;

import static me.vukas.common.base.Arrays.wrap;
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

    private <N, T> Element<N, T> diff(T original, T revised, N elementName, Class fieldType, Class containerType, Key<N, T> key) {
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

        if (this.visitedElements.contains(original)) {
            return null;    //TODO: should we return null or something else on circular reference
        }

        this.visitedElements.push(original);

        if (fieldType.isArray()) {
            List<Element<?, ?>> elements = new ArrayList<Element<?, ?>>();

            Object[] originalArray = wrap(original);
            Object[] revisedArray = wrap(revised);

            Set<Integer> matchedIndexes = new HashSet<Integer>();

            ORIGINAL_ARRAY:
            for (int i = 0; i < originalArray.length; i++) {
                Class elementType = originalArray[i] == null ? null : originalArray[i].getClass();
                Key<Integer, Object> elementKey = this.generateKey(i, elementType, fieldType, originalArray[i]);
                for (int j = 0; j < revisedArray.length; j++) {
                    if (!matchedIndexes.contains(j) && this.compare.compare(originalArray[i], revisedArray[j])) {
                        matchedIndexes.add(j);
                        Element<Integer, Object> element = this.diff(originalArray[i], revisedArray[j], j, elementType, fieldType, elementKey);
                        if (i != j) {
                            if (element.getStatus() == Element.Status.EQUAL) {
                                element.setStatus(Element.Status.EQUAL_MOVED);
                            } else {
                                element.setStatus(Element.Status.MODIFIED_MOVED);
                            }
                        }
                        elements.add(element);
                        continue ORIGINAL_ARRAY;
                    }
                }
                elements.add(new LeafElement<Integer, Object>(i, Element.Status.DELETED, elementKey, null));
            }

            for (int j = 0; j < revisedArray.length; j++) {
                if (!matchedIndexes.contains(j)) {
                    elements.add(new LeafElement<Integer, Object>(j, Element.Status.ADDED, null, revisedArray[j]));
                }
            }

            Element.Status status = determineElementStatus(elements);

            this.visitedElements.pop();
            Key<N, T> arrayKey = this.generateKey(elementName, fieldType, containerType, original);
            return new NodeElement<N, T>(elementName, status, arrayKey, elements);
        }

        for (EntityGeneration entityGeneration : this.entityGenerations) {
            if (entityGeneration.getType().isAssignableFrom(fieldType)) {
                Element<N, T> element = entityGeneration.diff(original, revised, elementName, fieldType, containerType, key);
                this.visitedElements.pop();
                return element;
            }
        }

        List<Element<?, ?>> elements = this.processFields(fieldType, original, revised);

        Element.Status status = determineElementStatus(elements);

        this.visitedElements.pop();
        return new NodeElement<N, T>(elementName, status, key, elements);
    }

    private <T> List<Element<?, ?>> processFields(Class fieldType, T original, T revised){
        try {
            return this.processAllClassFields(fieldType, original, revised);
        }
        catch (IllegalAccessException e){
            throw new UnsupportedOperationException(e);
        }
    }

    private <T> List<Element<?, ?>> processAllClassFields(Class fieldType, T original, T revised) throws IllegalAccessException{
        List<Element<?, ?>> elements = new ArrayList<Element<?, ?>>();
        List<Field> fields = getAllFields(fieldType);
        for (Field field : fields) {
            field.setAccessible(true);
            Key fieldKey = this.generateKey(field.getName(), field.getType(), field.getDeclaringClass(), field.get(original));
            Element element = this.diff(field.get(original), field.get(revised), field.getName(), field.getType(), field.getDeclaringClass(), fieldKey);
            elements.add(element);
        }
        return elements;
    }

    private static Element.Status determineElementStatus(List<Element<?, ?>> children) {
        for (Element<?, ?> element : children) {
            if (element.getStatus() != Element.Status.EQUAL) {
                return Element.Status.MODIFIED;
            }
        }
        return Element.Status.EQUAL;
    }

    private <N, T> Key<N, T> generateKey(N elementName, Class elementType, Class containerType, T value) {
        if (value == null) {
            return new LeafKey<N, T>(elementName, elementType, containerType, null);
        }
        return null;
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
            this.registerEntityGeneration(new CollectionEntityGeneration());
            this.registerEntityGeneration(new MapEntityGeneration());
            this.registerEntityGeneration(new MapEntryEntityGeneration());
        }

        public Diff build() {
            return new Diff(this);
        }
    }
}