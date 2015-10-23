package me.vukas.core.utils.entity.key;

import java.util.List;

public class ArrayNodeKey<N, V> extends NodeKey<N, V> {
    private int length;

    public ArrayNodeKey(N name, Class type, Class container, List<Key<N, V>> children, int length) {
        super(name, type, container, children);
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
