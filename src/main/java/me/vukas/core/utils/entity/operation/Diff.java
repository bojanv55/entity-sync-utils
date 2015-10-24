package me.vukas.core.utils.entity.operation;

import me.vukas.core.utils.entity.EntityDefinition;
import me.vukas.core.utils.entity.EntityGeneration;
import me.vukas.core.utils.entity.element.Element;
import me.vukas.core.utils.entity.element.LeafElement;
import me.vukas.core.utils.entity.key.Key;
import me.vukas.core.utils.entity.key.LeafKey;

import java.util.*;

public class Diff {
    private Compare compare;
    private final Stack<Object> visitedElements = new Stack<Object>();
    private final Stack<Object> visitedKeys = new Stack<Object>();
    private final Map<Class, EntityDefinition> typesToEntityDefinitions;
    private final List<EntityGeneration> entityGenerations;

    private Diff(Builder builder){
        this.typesToEntityDefinitions = builder.typesToEntityDefinitions;
        this.entityGenerations = builder.entityGenerations;

        for(EntityGeneration entityGeneration : this.entityGenerations){

        }
    }

    public <T> Element diff(T original, T revised) {
        Class originalClass = original == null ? null : original.getClass();
        Key rootKey = this.generateKey(Key.Name.ROOT, originalClass, null, original);
        return this.diff(original, revised, Element.Name.ROOT, originalClass, null, rootKey);
    }

    private <T, N> Element diff(T original, T revised, N elementName, Class fieldType, Class containerType, Key<N, T> key) {
        if (original == revised) {
            return new LeafElement<N, T>(elementName, Element.Status.EQUAL, key, revised);
        }
        if (original == null || revised == null) {
            return new LeafElement<N, T>(elementName, Element.Status.MODIFIED, key, revised);
        }

        return null;
    }

    private <T, N> Key generateKey(N elementName, Class elementType, Class containerType, T value) {
        if (value == null) {
            return new LeafKey<N, T>(elementName, elementType, containerType, null);
        }
        return null;
    }

    public static class Builder{
        private final Map<Class, EntityDefinition> typesToEntityDefinitions = new HashMap<Class, EntityDefinition>();
        private final List<EntityGeneration> entityGenerations = new ArrayList<EntityGeneration>();

        public Builder(){
            this.registerInternalEntityGenerations();
        }

        public Builder(List<EntityDefinition> entityDefinitions, List<EntityGeneration> entityGenerations){
            this();
            for(EntityDefinition entityDefinition : entityDefinitions){
                this.typesToEntityDefinitions.putIfAbsent(entityDefinition.getType(), entityDefinition);
            }
            this.entityGenerations.addAll(entityGenerations);
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

        public Builder registerEntityGeneration(EntityGeneration entityGeneration){
            this.entityGenerations.add(entityGeneration);
            return this;
        }

        public Builder registerEntityGenerations(List<EntityGeneration> entityGenerations){
            this.entityGenerations.addAll(entityGenerations);
            return this;
        }

        private void registerInternalEntityGenerations(){

        }

        public Diff build(){
            return new Diff(this);
        }
    }
}