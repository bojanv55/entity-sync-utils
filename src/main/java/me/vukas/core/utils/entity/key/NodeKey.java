package me.vukas.core.utils.entity.key;

import java.util.ArrayList;
import java.util.List;

public class NodeKey<N, V> extends Key<N, V> {
    private List<Key<N,V>> children = new ArrayList<Key<N, V>>();

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
