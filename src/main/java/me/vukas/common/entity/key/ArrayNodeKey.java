package me.vukas.common.entity.key;

import java.util.List;

public class ArrayNodeKey<N, V> extends NodeKey<N, V> {
    private final int length;

    public ArrayNodeKey(N name, Class type, Class container, List<Key<?, ?>> children, int length) {
        super(name, type, container, children);
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
