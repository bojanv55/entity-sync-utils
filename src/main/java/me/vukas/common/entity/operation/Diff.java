package me.vukas.common.entity.operation;

import me.vukas.common.entity.EntityComparison;
import me.vukas.common.entity.EntityDefinition;
import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.Name;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.element.LeafElement;
import me.vukas.common.entity.generation.collection.CollectionEntityGeneration;
import me.vukas.common.entity.generation.map.MapEntityGeneration;
import me.vukas.common.entity.generation.map.MapEntryEntityGeneration;
import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.key.LeafKey;

import java.util.*;

import static me.vukas.common.base.Arrays.wrap;
import static me.vukas.common.base.Objects.getWrappedClass;
import static me.vukas.common.base.Objects.isStringOrPrimitiveOrWrapped;

public class Diff {
    private Compare compare;
    private final Stack<Object> visitedElements = new Stack<Object>();
    private final Stack<Object> visitedKeys = new Stack<Object>();
    private final Map<Class, EntityDefinition> typesToEntityDefinitions;
    private final List<EntityGeneration> entityGenerations;

    @SuppressWarnings("unchecked")
    private Diff(Builder builder) {
        this.typesToEntityDefinitions = builder.typesToEntityDefinitions;
        this.entityGenerations = builder.entityGenerations;

        for (EntityGeneration entityGeneration : this.entityGenerations) {
            entityGeneration.setDiff(this);
        }

        this.compare = new Compare.Builder(new ArrayList<EntityDefinition>(this.typesToEntityDefinitions.values()),
                (List<EntityComparison>) (List<?>) this.entityGenerations).build();
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
            List<Element<Integer, Object>> elements = new ArrayList<Element<Integer, Object>>();

            Object[] originalArray = wrap(original);
            Object[] revisedArray = wrap(revised);

            Set<Integer> matchedIndexes = new HashSet<Integer>();

            ORIGINAL_ARRAY:
            for (int i = 0; i < originalArray.length; i++) {
                for (int j = 0; j < revisedArray.length; j++) {
                    if (!matchedIndexes.contains(j) && this.compare.compare(originalArray[i], revisedArray[j])) {
                        matchedIndexes.add(j);
                        Class matchedElementType = originalArray[i] == null ? null : originalArray[i].getClass();
                        Key<Integer, Object> matchedElementKey = this.generateKey(i, matchedElementType, fieldType, originalArray[i]);
                        Element<Integer, Object> element = this.diff(originalArray[i], revisedArray[j], j, matchedElementType, fieldType, matchedElementKey);
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
                Class matchedElementType = originalArray[i] == null ? null : originalArray[i].getClass();
                Key<Integer, Object> matchedElementKey = this.generateKey(i, matchedElementType, fieldType, originalArray[i]);
                elements.add(new LeafElement<Integer, Object>(i, Element.Status.DELETED, matchedElementKey, null));
            }
        }

        return null;
    }

    private <N, T> Key<N, T> generateKey(N elementName, Class elementType, Class containerType, T value) {
        if (value == null) {
            return new LeafKey<N, T>(elementName, elementType, containerType, null);
        }
        return null;
    }

    public static class Builder {
        private final Map<Class, EntityDefinition> typesToEntityDefinitions = new HashMap<Class, EntityDefinition>();
        private final List<EntityGeneration> entityGenerations = new ArrayList<EntityGeneration>();

        public Builder() {
            this.registerInternalEntityGenerations();
        }

        public Builder(List<EntityDefinition> entityDefinitions, List<EntityGeneration> entityGenerations) {
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

        public Builder registerEntityGenerations(List<EntityGeneration> entityGenerations) {
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