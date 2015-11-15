package me.vukas.common.entity.generation.map.element;

import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.key.Key;

import java.util.Map;

public class MapEntryNodeElement<N> extends Element<N, Map.Entry> {
    private Element elementKey;
    private Element elementValue;

    public MapEntryNodeElement(N name, Status status, Key<N, Map.Entry> key, Element elementKey, Element elementValue) {
        super(name, status, key);
        this.elementKey = elementKey;
        this.elementValue = elementValue;
    }

    public Element getElementKey() {
        return elementKey;
    }

    public Element getElementValue() {
        return elementValue;
    }
}
