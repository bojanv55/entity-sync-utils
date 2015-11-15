package me.vukas.common.entity.generation.array;

import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.element.LeafElement;
import me.vukas.common.entity.element.NodeElement;
import me.vukas.common.entity.generation.array.key.ArrayNodeKey;
import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.operation.Diff;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static me.vukas.common.base.Arrays.wrapCollectionOrMapOrPrimitiveArray;

public class ArrayEntityGeneration<T> extends EntityGeneration<T> {
    @Override
    public <N> Element<N, T> diff(T original, T revised, N elementName, Class fieldType, Class containerType, Key<N, T> key) {
        List<Element<?, ?>> elements = new ArrayList<Element<?, ?>>();

        Object[] originalArray = wrapCollectionOrMapOrPrimitiveArray(original);
        Object[] revisedArray = wrapCollectionOrMapOrPrimitiveArray(revised);

        Set<Integer> matchedIndexes = new HashSet<Integer>();

        ORIGINAL_ARRAY:
        for (int i = 0; i < originalArray.length; i++) {
            Class elementType = originalArray[i] == null ? null : originalArray[i].getClass();
            Key<Integer, Object> elementKey = this.getDiff().generateKey(i, elementType, fieldType, originalArray[i]);
            for (int j = 0; j < revisedArray.length; j++) {
                if (!matchedIndexes.contains(j) && this.getCompare().compare(originalArray[i], revisedArray[j])) {
                    matchedIndexes.add(j);
                    Element<Integer, Object> element = this.getDiff().diff(originalArray[i], revisedArray[j], j, elementType, fieldType, elementKey);
                    if (i != j) {
                        if (element.getStatus() == Element.Status.EQUAL) {
                            element.setStatus(Element.Status.EQUAL_MOVED);
                        } else {
                            element.setStatus(Element.Status.MODIFIED_MOVED);
                        }
                    }
                    elements.add(element);
                    continue ORIGINAL_ARRAY;
                }
            }
            elements.add(new LeafElement<Integer, Object>(i, Element.Status.DELETED, elementKey, null));
        }

        for (int j = 0; j < revisedArray.length; j++) {
            if (!matchedIndexes.contains(j)) {
                elements.add(new LeafElement<Integer, Object>(j, Element.Status.ADDED, null, revisedArray[j]));
            }
        }

        Element.Status status = Diff.determineElementStatus(elements);

        Key<N, T> arrayKey = this.generateKey(elementName, fieldType, containerType, original);
        return new NodeElement<N, T>(elementName, status, arrayKey, elements);
    }

    @Override
    public <N> Key<N, T> generateKey(N elementName, Class elementType, Class containerType, T value) {
        List<Key<?, ?>> keys = new ArrayList<Key<?, ?>>();
        Object[] originalArray = wrapCollectionOrMapOrPrimitiveArray(value);
        for(int i=0; i<originalArray.length; i++){
            Class keyType = originalArray[i] == null ? null : originalArray[i].getClass();
            keys.add(this.getDiff().generateKey(i, keyType, elementType, originalArray[i]));
        }
        return new ArrayNodeKey<N, T>(elementName, elementType, containerType, keys, originalArray.length);
    }

    @Override
    public boolean compare(T entity1, T entity2, Class fieldType) {
        Object[] entity1Array = wrapCollectionOrMapOrPrimitiveArray(entity1);
        Object[] entity2Array = wrapCollectionOrMapOrPrimitiveArray(entity2);

        if(entity1Array.length!=entity2Array.length){
            return false;
        }

        for(int i=0; i<entity1Array.length; i++){
            if(!this.getCompare().compare(entity1Array[i], entity2Array[i])){
                return false;
            }
        }

        return true;
    }
}
