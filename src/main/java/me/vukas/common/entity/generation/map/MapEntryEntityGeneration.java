package me.vukas.common.entity.generation.map;

import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.generation.map.element.MapEntryNodeElement;
import me.vukas.common.entity.generation.map.key.MapEntryNodeKey;
import me.vukas.common.entity.key.Key;

import java.util.AbstractMap;
import java.util.Map;

public class MapEntryEntityGeneration extends EntityGeneration<Map.Entry> {
    @Override
    public <N> Element<N, Map.Entry> diff(Map.Entry original, Map.Entry revised, N elementName, Class fieldType, Class containerType, Key<N, Map.Entry> key) {
        Class originalKeyClass = original.getKey() == null ? null : original.getKey().getClass();
        Key keyKey = this.getDiff().generateKey(elementName, originalKeyClass, fieldType, original.getKey());
        Element keyElement = this.getDiff().diff(this.getDiff().getRevisedIfCircularReference(original.getKey()), revised.getKey(), elementName, originalKeyClass, fieldType, keyKey);

        Class originalValueClass = original.getValue() == null ? null : original.getValue().getClass();
        Key valueKey = this.getDiff().generateKey(elementName, originalValueClass, fieldType, original.getValue());
        Element valueElement = this.getDiff().diff(this.getDiff().getRevisedIfCircularReference(original.getValue()), revised.getValue(), elementName, originalValueClass, fieldType, valueKey);

        Element.Status status = Element.Status.EQUAL;
        if(keyElement.getStatus() != Element.Status.EQUAL || valueElement.getStatus() != Element.Status.EQUAL){
            status = Element.Status.MODIFIED;
        }

        return new MapEntryNodeElement<N>(elementName, status, key, keyElement, valueElement);
    }

    @Override
    public <N> Key<N, Map.Entry> generateKey(N elementName, Class elementType, Class containerType, Map.Entry value) {
        Class keyClass = value.getKey() == null ? null : value.getKey().getClass();
        Class valueClass = value.getValue() == null ? null : value.getValue().getClass();
        Key keyKey = this.getDiff().generateKey(elementName, keyClass, elementType, value.getKey());
        Key keyValue = this.getDiff().generateKey(elementName, valueClass, elementType, value.getValue());

        return new MapEntryNodeKey<N>(elementName, elementType, containerType, keyKey, keyValue);
    }

    @Override
    public <N> Map.Entry patch(Map.Entry original, Element<N, Map.Entry> diff) {
        return new AbstractMap.SimpleEntry(
                this.getPatch().patch(original.getKey(), ((MapEntryNodeElement)diff).getElementKey()),
                this.getPatch().patch(original.getValue(), ((MapEntryNodeElement)diff).getElementValue())
        );
    }

    @Override
    public boolean compare(Map.Entry entity1, Map.Entry entity2, Class fieldType) {
        if(!this.getCompare().compare(entity1.getKey(), entity2.getKey())){
            return false;
        }
        if(!this.getCompare().compare(entity1.getValue(), entity2.getValue())){
            return false;
        }
        return true;
    }
}
