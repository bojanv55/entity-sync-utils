package me.vukas.common.entity.generation.array.key;

import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.key.NodeKey;

import java.lang.reflect.Array;
import java.util.*;

import static me.vukas.common.base.Arrays.insert;
import static me.vukas.common.base.Arrays.wrapCollectionOrMapOrPrimitiveArray;

public class ArrayNodeKey<N, V> extends NodeKey<N, V> {
    private final int length;

    public ArrayNodeKey(N name, Class type, Class container, List<Key<?, ?>> children, int length) {
        super(name, type, container, children);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean match(V value) {
        Object[] array = wrapCollectionOrMapOrPrimitiveArray(value);
        Set<Integer> visitedIndexes = new HashSet<Integer>();
        Iterator<Key<?, ?>> childIterator = this.getChildren().iterator();
        while (childIterator.hasNext()) {
            Key child = childIterator.next();
            if (!(array.length > (Integer) child.getName()) || !child.match(array[(Integer) child.getName()])) {
                INNER_LOOP:
                while (childIterator.hasNext()) {
                    child = childIterator.next();
                    for (int i = 0; i < array.length; i++) {
                        if (child.match(array[i]) && !visitedIndexes.contains(i)) {
                            visitedIndexes.add(i);
                            continue INNER_LOOP;
                        }
                    }
                    return false;
                }
            } else {
                visitedIndexes.add((Integer) child.getName());
            }
        }
        return true;
    }

    @Override
    public <T> T makeChild() throws NoSuchFieldException, IllegalAccessException {

        if (Collection.class.isAssignableFrom(this.getType())) {
            Collection newCollection = null;

                newCollection = new ArrayList();

            if(Set.class.isAssignableFrom(this.getType())){
                newCollection = new HashSet();
            }


            for(Key child : this.getChildren()){
                newCollection.add(child == null ? null : child.makeChild());
            }
            return (T) newCollection;
        } else if (Map.class.isAssignableFrom(this.getType())) {
            Map newMap = null;
                newMap = new HashMap();

            for(Key child : this.getChildren()){
                Map.Entry mapEntry = (Map.Entry) child.makeChild();
                newMap.put(mapEntry.getKey(), mapEntry.getValue());
            }

            return (T) newMap;
        }


        T newArray = (T) Array.newInstance(this.getType().getComponentType(), this.length);
        int index = 0;
        for(Key child : this.getChildren()){
            insert(newArray, index++, child.makeChild());
        }
        return newArray;
    }
}
