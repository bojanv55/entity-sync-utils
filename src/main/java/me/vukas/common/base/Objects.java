package me.vukas.common.base;

import java.lang.reflect.Constructor;
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

    public static <T> T defaultValue(Class<T> type){
        if (type == Byte.class) {
            return (T) new Byte((byte) 0);
        } else if (type == Short.class) {
            return (T) new Short((short) 0);
        } else if (type == Integer.class) {
            return (T) new Integer(0);
        } else if (type == Long.class) {
            return (T) new Long(0);
        } else if (type == Float.class) {
            return (T) new Float(0);
        } else if (type == Double.class) {
            return (T) new Double(0);
        } else if (type == Boolean.class) {
            return (T) new Boolean(false);
        } else if (type == Character.class) {
            return (T) new Character('\u0000');
        }
        return null;
    }

    public static  <T> T createNewObjectOfType(Class<T> type){
        if(type != null) {
            try {
                //return type.newInstance();
                return getFirstConstructor(type).newInstance();
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
        return null;
    }

    static <C> Constructor<C> getFirstConstructor(Class<C> c){
//        for(Constructor con : c.getDeclaredConstructors()){
//            Class[] types = con.getParameterTypes();
//            boolean match = true;
//            for(int i = 0; i < types.length; i++){
//                Class need = types[i], got = initArgs[i].getClass();
//                if(!need.isAssignableFrom(got)){
//                    if(need.isPrimitive()){
//                        match = (int.class.equals(need) && Integer.class.equals(got))
//                                || (long.class.equals(need) && Long.class.equals(got))
//                                || (char.class.equals(need) && Character.class.equals(got))
//                                || (short.class.equals(need) && Short.class.equals(got))
//                                || (boolean.class.equals(need) && Boolean.class.equals(got))
//                                || (byte.class.equals(need) && Byte.class.equals(got));
//                    }else{
//                        match = false;
//                    }
//                }
//                if(!match)
//                    break;
//            }
//            if(match)
//                return con;
//        }
        throw new IllegalArgumentException("Cannot find an appropriate constructor for class " + c);
    }
}
