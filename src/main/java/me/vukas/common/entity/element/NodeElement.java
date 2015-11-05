package me.vukas.common.entity.element;

import me.vukas.common.entity.key.Key;

import java.util.List;

public class NodeElement<N, V, CN, CV> extends Element<N, V> {
    private final List<Element<CN, CV>> children;

    public NodeElement(N name, Status status, Key<N, V> key, List<Element<CN, CV>> children) {
        super(name, status, key);
        this.children = children;
    }

    public List<Element<CN, CV>> getChildren() {
        return children;
    }
}
