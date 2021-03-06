package me.vukas.common.entity.operation;

import me.vukas.common.entity.EntityComparison;
import me.vukas.common.entity.EntityDefinition;
import me.vukas.common.entity.IgnoredFields;
import me.vukas.common.entity.generation.array.ArrayEntityGeneration;
import me.vukas.common.entity.generation.map.MapEntryEntityGeneration;

import java.lang.reflect.Field;
import java.util.*;

import static me.vukas.common.base.Objects.getAllFields;
import static me.vukas.common.base.Objects.isStringOrPrimitiveOrWrapped;

public class Compare {
    private final Stack<Object> visitedElements = new Stack<Object>();
    private final Map<Object, Object> allVisitedElements = new HashMap<Object, Object>();
    private final Map<Class, EntityDefinition> typesToEntityDefinitions;
    private final Map<Class, IgnoredFields> typesToIgnoredFields;
    private final List<EntityComparison<?>> entityComparisons;

    private Compare(Builder builder) {
        this.entityComparisons = builder.entityComparisons;
        this.typesToEntityDefinitions = builder.typesToEntityDefinitions;
        this.typesToIgnoredFields = builder.typesToIgnoredFields;

        for (EntityComparison<?> entityComparison : this.entityComparisons) {
            entityComparison.setCompare(this);
        }
    }

    public <T> boolean compare(T entity1, T entity2) {
        Class entity1Class = entity1 == null ? null : entity1.getClass();
        boolean result = this.compare(entity1, entity2, entity1Class);
        this.allVisitedElements.clear();
        return result;
    }

    public <T> boolean compare(T entity1, T entity2, Class fieldType) { //TODO: remove field type?
        if (entity1 == entity2) {
            return true;
        }

        if (entity1 == null || entity2 == null) {
            return false;
        }

        if (isStringOrPrimitiveOrWrapped(fieldType) || Enum.class.isAssignableFrom(fieldType)) {
            return entity1.equals(entity2);
        }

        if (this.visitedElements.contains(entity1) && this.allVisitedElements.containsKey(entity1)) {
//            if(this.allVisitedElements.get(entity1).contains(entity2)) {
//                return true;
//            }
            return this.allVisitedElements.get(entity1).equals(entity2);
        }
        this.visitedElements.push(entity1);

        if (fieldType.isArray() || Collection.class.isAssignableFrom(fieldType) || Map.class.isAssignableFrom(fieldType)) {
            EntityComparison<T> entityComparison = new ArrayEntityGeneration<T>(this);
            boolean equals = entityComparison.compare(entity1, entity2, fieldType);
            this.visitedElements.pop();
            return equals;
        }

        for (EntityComparison<?> entityComparison : this.entityComparisons) {
            if (entityComparison.getType().isAssignableFrom(fieldType)) { //TODO: class hierarchy priority
                EntityComparison<T> entityComparisonCasted = (EntityComparison<T>) entityComparison;
                boolean equals = entityComparisonCasted.compare(entity1, entity2, fieldType);
                this.visitedElements.pop();
                return equals;
            }
        }

        this.allVisitedElements.put(entity1, entity2);

        List<Field> fields;
        if (this.typesToEntityDefinitions.containsKey(fieldType)) {
            fields = this.typesToEntityDefinitions.get(fieldType).getFields();
        } else {
            fields = getAllFields(fieldType);
        }

        for (Field field : fields) {
            if (shouldTestFieldForEquality(fieldType, field.getDeclaringClass(), field)) {
                try {
                    field.setAccessible(true);
                    Class fieldClass = field.get(entity1) == null ? null : field.get(entity1).getClass();
                    if (!this.compare(field.get(entity1), field.get(entity2), fieldClass)) {
                        this.visitedElements.pop();
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    //TODO: remove this exception to separate method
                }
            }
        }

        this.visitedElements.pop();
        return true;
    }

    private boolean shouldTestFieldForEquality(Class container, Class declaring, Field field) {
        return !(this.typesToIgnoredFields.containsKey(container) && this.typesToIgnoredFields.get(container).containsField(declaring, field.getName()));
    }

    public static class Builder {
        private final Map<Class, EntityDefinition> typesToEntityDefinitions = new HashMap<Class, EntityDefinition>();
        private final Map<Class, IgnoredFields> typesToIgnoredFields = new HashMap<Class, IgnoredFields>();
        private final List<EntityComparison<?>> entityComparisons = new ArrayList<EntityComparison<?>>();

        public Builder() {
            this.registerInternalEntityComparisons();
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

        public Builder ignoreFields(IgnoredFields ignoredFields) {
            this.typesToIgnoredFields.putIfAbsent(ignoredFields.getType(), ignoredFields);
            return this;
        }

        public Builder registerEntityComparison(EntityComparison entityComparison) {
            this.entityComparisons.add(entityComparison);
            return this;
        }

        public Builder registerEntityGenerations(List<EntityComparison<?>> entityComparisons) {
            this.entityComparisons.addAll(entityComparisons);
            return this;
        }

        private void registerInternalEntityComparisons() {
            this.registerEntityComparison(new MapEntryEntityGeneration());
        }

        public Compare build() {
            return new Compare(this);
        }
    }
}
