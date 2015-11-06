package me.vukas.common.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Objects {
    public static boolean isWrapped(Class type) {
        return type == Byte.class
                || type == Short.class
                || type == Integer.class
                || type == Long.class
                || type == Float.class
                || type == Double.class
                || type == Boolean.class
                || type == Character.class;
    }

    public static boolean isPrimitiveOrWrapped(Class type) {
        return type.isPrimitive()
                || isWrapped(type);
    }

    public static boolean isStringOrPrimitiveOrWrapped(Class type) {
        return type == String.class
                || isPrimitiveOrWrapped(type);
    }

    public static Class getWrappedClass(Class type) {
        if (type == byte.class) {
            return Byte.class;
        } else if (type == short.class) {
            return Short.class;
        } else if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == char.class) {
            return Character.class;
        }
        return type;
    }

    public static List<Field> getAllFields(Class type) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(java.util.Arrays.asList(type.getDeclaredFields()));
        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }
        return fields;
    }
}
