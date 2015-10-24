package me.vukas.core.utils.entity;

import me.vukas.core.utils.entity.element.Element;
import me.vukas.core.utils.entity.key.Key;
import me.vukas.core.utils.entity.operation.Diff;

public abstract class EntityGeneration<T> extends EntityComparison<T> {
    private Diff diff;

    public Diff getDiff(){
        return this.diff;
    }

    protected void setDiff(Diff diff){
        this.diff = diff;
    }

    public abstract <N> Element diff(T original, T revised, N elementName, Class fieldType, Class containerType, Key<N, T> key);
    public abstract <N> Key generateKey(N elementName, Class elementType, Class containerType, T value);
}