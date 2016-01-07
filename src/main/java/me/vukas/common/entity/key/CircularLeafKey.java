package me.vukas.common.entity.key;

import me.vukas.common.base.Objects;
import me.vukas.common.entity.element.LeafElement;

import java.util.ArrayList;
import java.util.List;

public class CircularLeafKey<N, V> extends LeafKey<N, V>  {
    private final List<LeafElement<?, ?>> circularLeafElements;

    public CircularLeafKey(N name, Class type, Class container, V value) {
        super(name, type, container, value);
        this.circularLeafElements = new ArrayList<LeafElement<?, ?>>();
    }

    @Override
    public void setValue(V value) {
        super.setValue(value);
    }

    public void registerCircularElement(LeafElement<?, ?> element) {
        this.circularLeafElements.add(element);
    }

    @Override
    public boolean match(V value) {
        for (LeafElement element : this.circularLeafElements) {
            element.setValue(value);
        }
        return true;
    }
}
