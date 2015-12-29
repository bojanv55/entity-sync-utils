package me.vukas.common.entity.operation;

import me.vukas.common.entity.EntityDefinition;
import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.element.LeafElement;
import me.vukas.common.entity.element.NodeElement;
import me.vukas.common.entity.generation.array.ArrayEntityGeneration;
import me.vukas.common.entity.generation.map.MapEntryEntityGeneration;

import java.lang.reflect.Field;
import java.util.*;

public class Patch {
    private final List<EntityGeneration<?>> entityGenerations;

    @SuppressWarnings("unchecked")
    private Patch(Builder builder) {
        this.entityGenerations = builder.entityGenerations;

        for (EntityGeneration entityGeneration : this.entityGenerations) {
            entityGeneration.setPatch(this);
        }
    }

    public <N, T> T patch(T original, Element<N, T> diff){
        Class originalType = diff.getKey() == null ? null : diff.getKey().getType();

        if(diff.getKey()!=null && !diff.getKey().match(original)){
            throw new UnsupportedOperationException("Key does not match");
        }

        if(diff instanceof LeafElement){
            return ((LeafElement<?, T>) diff).getValue();
        }

        for(EntityGeneration<?> entityGeneration : this.entityGenerations){
            if(entityGeneration.getType().isAssignableFrom(originalType)){   //TODO: class hierarchy priority
                EntityGeneration<T> entityGenerationCasted = (EntityGeneration<T>)entityGeneration;
                return entityGenerationCasted.patch(original, diff);
            }
        }

        if(originalType.isArray() || Collection.class.isAssignableFrom(originalType)
                || Map.class.isAssignableFrom(originalType)){
            EntityGeneration<T> entityGeneration = new ArrayEntityGeneration<T>(this);
            return entityGeneration.patch(original, diff);
        }

        //TODO: must be an object?
        for(Object childElement : ((NodeElement)diff).getChildren()){
            Field field = ((Element)childElement).getKey().getAccessibleDeclaredFiled();
            try {
                field.set(original, this.patch(field.get(original), (Element)childElement));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return original;
    }

    public static class Builder {
        private final List<EntityGeneration<?>> entityGenerations = new ArrayList<EntityGeneration<?>>();

        public Builder() {
            this.registerInternalEntityGenerations();
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

        public Patch build() {
            return new Patch(this);
        }
    }
}
