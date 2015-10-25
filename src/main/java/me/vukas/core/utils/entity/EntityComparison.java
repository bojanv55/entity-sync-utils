package me.vukas.core.utils.entity;

import me.vukas.core.utils.entity.operation.Compare;

import java.lang.reflect.ParameterizedType;

public abstract class EntityComparison<T> {
    private final Class<T> type;
    private Compare compare;

    @SuppressWarnings("unchecked")
    protected EntityComparison(){
        this.type = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Class<T> getType() {
        return type;
    }

    public Compare getCompare() {
        return compare;
    }

    public void setCompare(Compare compare){
        assert this.compare == null : "Compare field can be set only once";
        this.compare = compare;
    }

    public abstract boolean areEqual(T entity1, T entity2, Class fieldType);
}
