package me.vukas.common.entity.key;

import me.vukas.common.entity.Name;

public class LeafKey<N, V> extends Key<N, V> {
    private V value;

    public LeafKey(N name, Class type, Class container, V value) {
        super(name, type, container);
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean match(V value) {
        return this.value == value || this.value != null && (this.value.equals(value) || this.value.equals(Name.CIRCULAR_REFERENCE));
    }

    @Override
    public <T> T makeChild() throws NoSuchFieldException, IllegalAccessException {
        return (T) this.value;
    }
}
