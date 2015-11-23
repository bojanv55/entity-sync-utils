package me.vukas.common.entity;

import me.vukas.common.entity.operation.Compare;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;

public abstract class EntityComparison<T> {
    private Class<T> type;
    private Compare compare;

    @SuppressWarnings("unchecked")
    protected EntityComparison() {
        try {
            this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        catch (ClassCastException e){
            this.type = null;   //TODO: in case we need to init int[] as <T>
        }
    }

    public Class<T> getType() {
        return type;
    }

    public Compare getCompare() {
        return compare;
    }

    public void setCompare(Compare compare) {
        assert this.compare == null : "Compare field can be set only once";
        this.compare = compare;
    }

    public abstract boolean compare(T entity1, T entity2, Class fieldType);
}
