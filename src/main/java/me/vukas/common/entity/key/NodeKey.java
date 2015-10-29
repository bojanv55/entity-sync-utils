package me.vukas.common.entity.key;

import java.util.List;

public class NodeKey<N, V> extends Key<N, V> {
    private final List<Key<N,V>> children;

    public NodeKey(N name, Class type, Class container, List<Key<N, V>> children) {
        super(name, type, container);
        this.children = children;
    }

    public List<Key<N, V>> getChildren() {
        return children;
    }

    @Override
    public boolean match(V value) {
        return false;
    }
}
