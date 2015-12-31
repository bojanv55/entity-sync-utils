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
        Class revisedKeyClass = revised.getKey() == null ? null : revised.getKey().getClass();
        Key keyKey = this.getDiff().generateKey(elementName, revisedKeyClass, fieldType, original == null ? null : original.getKey());
        Element keyElement = this.getDiff().diff(original == null ? null : this.getDiff().getRevisedIfCircularReference(original.getKey()), this.getDiff().getRevisedIfCircularReference(revised.getKey()), elementName, revisedKeyClass, fieldType, keyKey);

        Class revisedValueClass = revised.getValue() == null ? null : revised.getValue().getClass();
        Key valueKey = this.getDiff().generateKey(elementName, revisedValueClass, fieldType, original == null ? null : original.getValue());
        Element valueElement = this.getDiff().diff(original == null ? null : this.getDiff().getRevisedIfCircularReference(original.getValue()), this.getDiff().getRevisedIfCircularReference(revised.getValue()), elementName, revisedValueClass, fieldType, valueKey);

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
                this.getPatch().patch(original == null ? null : original.getKey(), ((MapEntryNodeElement)diff).getElementKey()),
                this.getPatch().patch(original == null ? null : original.getValue(), ((MapEntryNodeElement)diff).getElementValue())
        );
    }

    @Override
    public boolean compare(Map.Entry entity1, Map.Entry entity2, Class fieldType) {
        Class entity1KeyClass = entity1.getKey() == null ? null : entity1.getKey().getClass();
        if(!this.getCompare().compare(entity1.getKey(), entity2.getKey(), entity1KeyClass)){
            return false;
        }
        Class entity1ValueClass = entity1.getValue() == null ? null : entity1.getValue().getClass();
        if(!this.getCompare().compare(entity1.getValue(), entity2.getValue(), entity1ValueClass)){
            return false;
        }
        return true;
    }
}
