package me.vukas.common.entity;

import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.operation.Diff;
import me.vukas.common.entity.operation.Patch;

public abstract class EntityGeneration<T> extends EntityComparison<T> {
    private Diff diff;
    private Patch patch;

    public Diff getDiff() {
        return this.diff;
    }

    public Patch getPatch() {
        return this.patch;
    }

    public void setDiff(Diff diff) {
        assert this.diff == null : "Diff field can be set only once";
        this.diff = diff;
    }

    public void setPatch(Patch patch) {
        assert this.patch == null : "Patch field can be set only once";
        this.patch = patch;
    }

    public abstract <N> Element<N, T> diff(T original, T revised, N elementName, Class fieldType, Class containerType, Key<N, T> key);

    public abstract <N> Key<N, T> generateKey(N elementName, Class elementType, Class containerType, T value);

    public abstract <N> T patch(T original, Element<N, T> diff);
}