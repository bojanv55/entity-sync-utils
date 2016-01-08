package me.vukas.common.entity.key;

import java.lang.reflect.Field;

public abstract class Key<N, V> {
    private final N name;
    private final Class type;
    private final Class container;

    public Key(N name, Class type, Class container) {
        this.name = name;
        this.type = type;
        this.container = container;
    }

    public N getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public Class getContainer() {
        return container;
    }

    public Field getAccessibleDeclaredFiled() {
        return this.getAccessibleDeclaredFiled((String) this.getName());
    }

    public Field getAccessibleDeclaredFiled(String fieldName) {
        try {
            Field field = this.getContainer().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new UnsupportedOperationException("Field not found during patch process");
        }
    }

    public abstract boolean match(V value);

    public abstract <T> T makeChild() throws NoSuchFieldException, IllegalAccessException;
}
