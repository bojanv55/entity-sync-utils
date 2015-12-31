package me.vukas.common.entity;

import java.util.HashMap;
import java.util.Map;

public class IgnoredFields {
    private final Class type;
    private final Map<Class, String[]> typesToProperties = new HashMap<Class, String[]>();

    public IgnoredFields(Class type, String... properties) {
        this.type = type;
        this.typesToProperties.put(type, properties);
    }

    public Class getType() {
        return this.type;
    }

    public boolean containsField(Class type, String fieldName) {
        if (this.typesToProperties.containsKey(type)) {
            for (String field : this.typesToProperties.get(type)) {
                if (field.equals(fieldName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public IgnoredFields registerSuperclass(Class type, String... properties) {
        this.typesToProperties.putIfAbsent(type, properties);
        return this;
    }
}
