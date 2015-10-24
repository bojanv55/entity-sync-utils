package me.vukas.core.utils.entity.element;

import me.vukas.core.utils.entity.key.Key;

public abstract class Element<N, V> {
    private final N name;
    private Status status;
    private final Key<N, V> key;

    public Element(N name, Status status, Key<N, V> key) {
        this.name = name;
        this.status = status;
        this.key = key;
    }

    public N getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public Key<N, V> getKey() {
        return key;
    }

    public enum Name{
        ROOT
    }

    public enum Status{
        EQUAL,
        EQUAL_MOVED,
        MODIFIED,
        MODIFIED_MOVED,
        ADDED,
        DELETED
    }
}
