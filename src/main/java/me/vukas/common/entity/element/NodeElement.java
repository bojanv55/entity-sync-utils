package me.vukas.common.entity.element;

import me.vukas.common.entity.key.Key;

import java.util.List;

public class NodeElement<N, V> extends Element<N, V> {
    private final List<Element<N, V>> children;

    public NodeElement(N name, Status status, Key<N, V> key, List<Element<N, V>> children) {
        super(name, status, key);
        this.children = children;
    }

    public List<Element<N, V>> getChildren() {
        return children;
    }
}
