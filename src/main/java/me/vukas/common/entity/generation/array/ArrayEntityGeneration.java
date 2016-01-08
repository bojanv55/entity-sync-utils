package me.vukas.common.entity.generation.array;

import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.element.LeafElement;
import me.vukas.common.entity.element.NodeElement;
import me.vukas.common.entity.generation.array.key.ArrayNodeKey;
import me.vukas.common.entity.generation.map.key.MapEntryNodeKey;
import me.vukas.common.entity.key.CircularLeafKey;
import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.key.LeafKey;
import me.vukas.common.entity.key.NodeKey;
import me.vukas.common.entity.operation.Compare;
import me.vukas.common.entity.operation.Diff;
import me.vukas.common.entity.operation.Patch;

import java.util.*;

import static me.vukas.common.base.Arrays.*;
import static me.vukas.common.base.Objects.createNewObjectOfType;

public class ArrayEntityGeneration<T> extends EntityGeneration<T> {

    public ArrayEntityGeneration(Diff diff, Compare compare) {
        this(compare);
        this.setDiff(diff);
    }

    public ArrayEntityGeneration(Patch patch) {
        super();
        this.setPatch(patch);
    }

    public ArrayEntityGeneration(Compare compare) {
        super();
        this.setCompare(compare);
    }

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
                Class entity1Class = originalArray[i] == null ? null : originalArray[i].getClass();
                if (!matchedIndexes.contains(j) && this.getCompare().compare(this.getDiff().getRevisedIfCircularReference(originalArray[i]), revisedArray[j], entity1Class)) {
                    matchedIndexes.add(j);
                    Element<Integer, Object> element = this.getDiff().diff(this.getDiff().getRevisedIfCircularReference(originalArray[i]), revisedArray[j], j, elementType, fieldType, elementKey);
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
                Class elementType = revisedArray[j] == null ? null : revisedArray[j].getClass();
                Object ob = createNewObjectOfType(elementType);

                if(elementType!=null && elementType.getName().equals("java.util.HashMap$Node")){
                    ob = new AbstractMap.SimpleEntry(createNewObjectOfType(((Map.Entry)revisedArray[j]).getKey() == null ? null :((Map.Entry)revisedArray[j]).getKey().getClass()), createNewObjectOfType(((Map.Entry)revisedArray[j]).getValue() == null ? null :((Map.Entry)revisedArray[j]).getValue().getClass()));
                }

                Key<Integer, Object> elementKey = this.getDiff().generateKey(j, elementType, fieldType, this.getDiff().registerNewToOriginalElement(ob, revisedArray[j]));
                Element element = this.getDiff().diff(this.getDiff().registerNewToOriginalElement(ob, revisedArray[j]), revisedArray[j], j, elementType, fieldType, elementKey);
                element.setStatus(Element.Status.ADDED);
                elements.add(element);
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
        for (int i = 0; i < originalArray.length; i++) {
            Class keyType = originalArray[i] == null ? null : originalArray[i].getClass();
            keys.add(this.getDiff().generateKey(i, keyType, elementType, originalArray[i]));
        }
        return new ArrayNodeKey<N, T>(elementName, elementType, containerType, keys, originalArray.length);
    }

    @Override
    public <N> T patch(T original, Element<N, T> diff) {
        Class originalType = diff.getKey() == null ? null : diff.getKey().getType();
        Object[] originalArray = wrapCollectionOrMapOrPrimitiveArray(original);

        if (((ArrayNodeKey) diff.getKey()).getLength() != originalArray.length
                || !diff.getKey().match(original)) { //TODO: check if getkey match is needed here -- possible previous checking, should not check 2 times
            throw new UnsupportedOperationException("Array size and/or element mismatch");
        }

        Set<Integer> skipIndexes = new TreeSet<Integer>();
        int newLength = originalArray.length;
        for (Element<?, ?> childElement : ((NodeElement<?, ?>) diff).getChildren()) {
            if (childElement.getStatus() == Element.Status.ADDED) {
                newLength++;
            } else if (childElement.getStatus() == Element.Status.DELETED) {
                newLength--;
            }

            if (childElement.getStatus() == Element.Status.DELETED
                    || childElement.getStatus() == Element.Status.EQUAL_MOVED
                    || childElement.getStatus() == Element.Status.MODIFIED_MOVED) {
                skipIndexes.add((Integer) childElement.getKey().getName());
            }
        }

        int[] skipIndexesUnwrapped = (int[]) unwrap(skipIndexes.toArray(new Integer[skipIndexes.size()]));
        Object newArray = partialCopy(originalArray, newLength, skipIndexesUnwrapped);
        for (Element childElement : ((NodeElement<?, ?>) diff).getChildren()) {
            if (childElement.getStatus() == Element.Status.EQUAL || childElement.getStatus() == Element.Status.EQUAL_MOVED) {

                if (!childElement.getKey().match(originalArray[(Integer) childElement.getKey().getName()])) {
                    return tryPatchingAsUnorderedCollection((NodeElement<?, ?>) diff, originalType, originalArray);
                }

                //collection is ordered so we can process it as usual
                insert(newArray, (Integer) childElement.getName(), originalArray[(Integer) childElement.getKey().getName()]);

            } else if (childElement.getStatus() == Element.Status.MODIFIED || childElement.getStatus() == Element.Status.MODIFIED_MOVED) {

                if (!childElement.getKey().match(originalArray[(Integer) childElement.getKey().getName()])) {
                    return tryPatchingAsUnorderedCollection((NodeElement<?, ?>) diff, originalType, originalArray);
                }

                //collection is ordered so we can process it as usual
                insert(newArray, (Integer) childElement.getName(), this.getPatch().patch(originalArray[(Integer) childElement.getKey().getName()], childElement));
            } else if (childElement.getStatus() == Element.Status.ADDED) {

                Object ob = (T) createNewObjectOfType(childElement.getKey().getType());

                try {
                    ob = childElement.getKey().makeChild();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if(childElement.getKey() instanceof LeafKey){
                    ob = ((LeafKey)(childElement.getKey())).getValue() == null ? (T) createNewObjectOfType(childElement.getKey().getType()) : ((LeafKey)(childElement.getKey())).getValue();
                }

                if(childElement.getKey() instanceof MapEntryNodeKey){
                    ob = new AbstractMap.SimpleEntry(createNewObjectOfType(((MapEntryNodeKey) childElement.getKey()).getKeyKey().getType()), createNewObjectOfType(((MapEntryNodeKey) childElement.getKey()).getKeyValue().getType()));
                }

                insert(newArray, (Integer) childElement.getName(), this.getPatch().patch(ob, childElement));
            }
        }

        return (T) unwrapCollectionOrMapOrPrimitiveArray(newArray, originalType);
    }

    private T tryPatchingAsUnorderedCollection(NodeElement<?, ?> diff, Class originalType, Object[] originalArray) {
        //this means that collection is not ordered!
        Collection newCollection = new ArrayList<Object>(Arrays.asList(originalArray));

        UNORDERED_COLLECTION:
        for (Element childElement : diff.getChildren()) {
            if (childElement.getStatus() == Element.Status.EQUAL || childElement.getStatus() == Element.Status.EQUAL_MOVED) {
                Iterator iterator = newCollection.iterator();
                while (iterator.hasNext()) {
                    Object newElement = iterator.next();
                    if (childElement.getKey().match(newElement)) {
                        continue UNORDERED_COLLECTION;
                    }
                }
                throw new RuntimeException("Cannot even find it if threaten as unordered");
            } else if (childElement.getStatus() == Element.Status.MODIFIED || childElement.getStatus() == Element.Status.MODIFIED_MOVED) {
                Iterator iterator = newCollection.iterator();
                while (iterator.hasNext()) {
                    Object newElement = iterator.next();
                    if (childElement.getKey().match(newElement)) {
                        iterator.remove();
                        newCollection.add(this.getPatch().patch(newElement, childElement));
                        continue UNORDERED_COLLECTION;
                    }
                }
                throw new RuntimeException("Cannot even find it if threaten as unordered");
            } else if (childElement.getStatus() == Element.Status.DELETED) {
                Iterator iterator = newCollection.iterator();
                while (iterator.hasNext()) {
                    Object newElement = iterator.next();
                    if (childElement.getKey().match(newElement)) {
                        iterator.remove();
                        continue UNORDERED_COLLECTION;
                    }
                }
                throw new RuntimeException("Cannot even find it if threaten as unordered");
            } else if (childElement.getStatus() == Element.Status.ADDED) {

                Object ob = (T) createNewObjectOfType(childElement.getKey().getType());

                if(childElement.getKey() instanceof MapEntryNodeKey){
                    ob = new AbstractMap.SimpleEntry(createNewObjectOfType(((MapEntryNodeKey) childElement.getKey()).getKeyKey().getType()), createNewObjectOfType(((MapEntryNodeKey) childElement.getKey()).getKeyValue().getType()));
                }


                newCollection.add(this.getPatch().patch(ob, childElement));
            }
        }

        return (T) unwrapCollectionOrMapOrPrimitiveArray(newCollection.toArray(), originalType);
    }

    @Override
    public boolean compare(T entity1, T entity2, Class fieldType) {
        Object[] entity1Array = wrapCollectionOrMapOrPrimitiveArray(entity1);
        Object[] entity2Array = wrapCollectionOrMapOrPrimitiveArray(entity2);

        if (entity1Array.length != entity2Array.length) {
            return false;
        }

        for (int i = 0; i < entity1Array.length; i++) {
            Class entity1Class = entity1Array[i] == null ? null : entity1Array[i].getClass();
            if (!this.getCompare().compare(entity1Array[i], entity2Array[i], entity1Class)) {   //TODO: check if this is real array - if it is, it must be ordered!

                //we possibly compare unordered collections so switch to that mode
                Set<Integer> visitedIndexes = new HashSet<Integer>();
                INNER_LOOP:
                for (int j = i; j < entity1Array.length; j++) {
                    for (int k = i; k < entity2Array.length; k++) {
                        entity1Class = entity1Array[j] == null ? null : entity1Array[j].getClass();
                        if (this.getCompare().compare(entity1Array[j], entity2Array[k], entity1Class) && !visitedIndexes.contains(k)) {
                            visitedIndexes.add(k);
                            continue INNER_LOOP;
                        }
                    }
                    return false;
                }

                return true;
            }
        }

        return true;
    }
}
