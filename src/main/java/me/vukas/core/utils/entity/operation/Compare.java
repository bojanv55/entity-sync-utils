package me.vukas.core.utils.entity.operation;

import me.vukas.core.utils.entity.EntityComparison;
import me.vukas.core.utils.entity.EntityDefinition;
import me.vukas.core.utils.entity.generation.collection.CollectionEntityGeneration;
import me.vukas.core.utils.entity.generation.map.MapEntityGeneration;
import me.vukas.core.utils.entity.generation.map.MapEntryEntityGeneration;

import java.util.*;

public class Compare {
    private final Stack<Object> visitedElements = new Stack<Object>();
    private final Map<Class, EntityDefinition> typesToEntityDefinitions;
    private final List<EntityComparison> entityComparisons;

    private Compare(Builder builder){
        this.entityComparisons = builder.entityComparisons;
        this.typesToEntityDefinitions = builder.typesToEntityDefinitions;

        for(EntityComparison entityComparison : this.entityComparisons){
            entityComparison.setCompare(this);
        }
    }

    public static class Builder{
        private final Map<Class, EntityDefinition> typesToEntityDefinitions = new HashMap<Class, EntityDefinition>();
        private final List<EntityComparison> entityComparisons = new ArrayList<EntityComparison>();

        public Builder(){
            this.registerInternalEntityComparisons();
        }

        public Builder(List<EntityDefinition> entityDefinitions, List<EntityComparison> entityComparisons){
            this();
            for(EntityDefinition entityDefinition : entityDefinitions){
                this.typesToEntityDefinitions.putIfAbsent(entityDefinition.getType(), entityDefinition);
            }
            this.entityComparisons.addAll(entityComparisons);
        }

        public Builder registerEntity(EntityDefinition entityDefinition){
            this.typesToEntityDefinitions.putIfAbsent(entityDefinition.getType(), entityDefinition);
            return this;
        }

        public Builder registerEntities(List<EntityDefinition> entityDefinitions){
            for(EntityDefinition entityDefinition : entityDefinitions){
                this.typesToEntityDefinitions.putIfAbsent(entityDefinition.getType(), entityDefinition);
            }
            return this;
        }

        public Builder registerEntityComparison(EntityComparison entityComparison){
            this.entityComparisons.add(entityComparison);
            return this;
        }

        public Builder registerEntityGenerations(List<EntityComparison> entityComparisons){
            this.entityComparisons.addAll(entityComparisons);
            return this;
        }

        private void registerInternalEntityComparisons(){
            this.registerEntityComparison(new CollectionEntityGeneration());
            this.registerEntityComparison(new MapEntityGeneration());
            this.registerEntityComparison(new MapEntryEntityGeneration());
        }

        public Compare build(){
            return new Compare(this);
        }
    }
}
