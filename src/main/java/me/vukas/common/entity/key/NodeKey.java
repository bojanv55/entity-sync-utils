package me.vukas.common.entity.key;

import java.lang.reflect.Field;
import java.util.List;

public class NodeKey<N, V> extends CircularKey<N, V> {
    private final List<Key<?, ?>> children;

    public NodeKey(N name, Class type, Class container, List<Key<?, ?>> children) {
        super(name, type, container);
        this.children = children;
    }

    public List<Key<?, ?>> getChildren() {
        return children;
    }

    @Override
    public boolean match(V value) {
        try {
            return this.matchKey(value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean matchKey(V value) throws NoSuchFieldException, IllegalAccessException {
        for (Key child : this.children) {
            String fieldName = (String) child.getName();
            Field field = child.getContainer().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object childObject = field.get(value);
            if (!child.match(childObject)) {
                return false;
            }
        }
        this.updateCircularReferences(value);
        return true;
    }
}
