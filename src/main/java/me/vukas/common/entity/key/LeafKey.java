package me.vukas.common.entity.key;

public class LeafKey<N, V> extends Key<N, V> {
    private final V value;

    public LeafKey(N name, Class type, Class container, V value) {
        super(name, type, container);
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean match(V value) {
        return this.value == value || this.value.equals(value);
    }
}
