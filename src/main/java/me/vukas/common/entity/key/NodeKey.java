package me.vukas.common.entity.key;

import me.vukas.common.entity.Name;

import java.lang.reflect.Field;
import java.util.List;

public class NodeKey<N, V> extends Key<N, V> {
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
        }
        catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean matchKey(V value) throws NoSuchFieldException, IllegalAccessException {
        for(Key child : this.children){
            if(child.getName().equals(Name.CIRCULAR_REFERENCE)){
                return true;
            }
            String fieldName = (String)child.getName();
            Field field = child.getContainer().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object childObject = field.get(value);
            if(!child.match(childObject)){
                return false;
            }
        }
        return true;
    }
}
