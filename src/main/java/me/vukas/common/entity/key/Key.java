package me.vukas.common.entity.key;

public abstract class Key<N, V> {
    private final N name;
    private final Class type;
    private final Class container;

    public Key(N name, Class type, Class container) {
        this.name = name;
        this.type = type;
        this.container = container;
    }

    public N getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public Class getContainer() {
        return container;
    }

    public abstract boolean match(V value);
}
