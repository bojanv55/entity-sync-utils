package me.vukas.core.utils.entity.element;

import me.vukas.core.utils.entity.key.Key;

public class LeafElement<N, V> extends Element<N, V> {
    private final V value;

    public LeafElement(N name, Status status, Key<N, V> key, V value) {
        super(name, status, key);
        this.value = value;
    }

    public V getValue() {
        return value;
    }
}
