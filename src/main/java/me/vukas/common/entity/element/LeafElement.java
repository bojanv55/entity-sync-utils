package me.vukas.common.entity.element;

import me.vukas.common.entity.key.Key;

public class LeafElement<N, V> extends Element<N, V> {
    private V value;

    public LeafElement(N name, Status status, Key<N, V> key, V value) {
        super(name, status, key);
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
