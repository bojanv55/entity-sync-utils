package me.vukas.core.utils.entity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityDefinition {
    private final Class type;
    private final Map<Class, String[]> typesToProperties = new HashMap<Class, String[]>();

    public EntityDefinition(Class type, String... properties) {
        this.type = type;
        this.typesToProperties.put(type, properties);
    }

    public Class getType() {
        return this.type;
    }

    public List<Field> getFields() {
        List<Field> fields = new ArrayList<Field>();
        for (Map.Entry<Class, String[]> entry : this.typesToProperties.entrySet()) {
            for (String fieldName : entry.getValue()) {
                fields.add(this.getDeclaredField(entry.getKey(), fieldName));
            }
        }
        return fields;
    }

    private Field getDeclaredField(Class type, String fieldName) {
        try {
            return type.getDeclaredField(fieldName);
        } catch (NoSuchFieldException exception) {
            throw new RuntimeException(String.format("Unknown field '%s' in type '%s'", fieldName, type.getName()));
        }
    }

    public EntityDefinition registerSuperclass(Class type, String... properties) {
        this.typesToProperties.putIfAbsent(type, properties);
        return this;
    }
}
