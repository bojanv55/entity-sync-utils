package me.vukas.common.entity.key;

import me.vukas.common.entity.element.LeafElement;

import java.util.ArrayList;
import java.util.List;

public abstract class CircularKey<N, V> extends Key<N, V> {
    private final List<LeafElement<?, ?>> circularLeafElements;
    private final List<LeafKey<?, ?>> circularLeafKeys;

    public CircularKey(N name, Class type, Class container) {
        super(name, type, container);
        this.circularLeafElements = new ArrayList<LeafElement<?, ?>>();
        this.circularLeafKeys = new ArrayList<LeafKey<?, ?>>();
    }

    public void registerCircularElement(LeafElement<?, ?> element) {
        this.circularLeafElements.add(element);
    }

    public void registerCircularKey(LeafKey<?, ?> key) {
        this.circularLeafKeys.add(key);
    }

    public void updateCircularReferences(V value) {
        for (LeafElement element : this.circularLeafElements) {
            element.setValue(value);
        }
        for (LeafKey key : this.circularLeafKeys) {
            key.setValue(value);
        }
    }
}
