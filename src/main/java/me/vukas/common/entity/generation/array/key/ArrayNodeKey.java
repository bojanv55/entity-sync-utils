package me.vukas.common.entity.generation.array.key;

import me.vukas.common.entity.key.Key;
import me.vukas.common.entity.key.NodeKey;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
}
