package me.vukas.common.base;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public class Arrays {
    @SuppressWarnings("unchecked")
    public static <T> T partialCopy(T array, int newLength, int[] skipIndexes) {
        if (array == null || !array.getClass().isArray()) {
            throw new IllegalArgumentException("Parameter 'array' must be an Array");
        }
        int start = 0;
        int length = Array.getLength(array);
        Class componentType = array.getClass().getComponentType();
        T newArray = (T) Array.newInstance(componentType, newLength > 0 ? newLength : 0);
        int j = 0;
        for (int i = 0; i < length; i++) {
            if (skipIndexes.length > j && i == skipIndexes[j]) {
                if (start == skipIndexes[j]) {
                    start++;
                } else {
                    System.arraycopy(array, start, newArray, start, skipIndexes[j] - start);
                    start = skipIndexes[j] + 1;
                }
                j++;
            }
        }
        if (start != length) {
            System.arraycopy(array, start, newArray, start, length - start);
        }
        return newArray;
    }

    public static Object[] wrapCollectionOrMapOrPrimitiveArray(Object input){
        if(input == null){
            throw new IllegalArgumentException("Parameter 'input' can not be null");
        }
        if(Collection.class.isAssignableFrom(input.getClass())){
            return ((Collection)input).toArray();
        }
        else if(Map.class.isAssignableFrom(input.getClass())){
            return ((Map)input).entrySet().toArray();
        }
        else{
            return wrap(input);
        }
    }

    public static Object[] wrap(Object array) {
        if (array == null || !array.getClass().isArray()) {
            throw new IllegalArgumentException("Parameter 'array' must be an Array");
        }
        Class componentType = array.getClass().getComponentType();
        if (componentType.isPrimitive()) {
            Object[] wrappedArray = null;
            if (componentType == byte.class) {
                byte[] originalArray = (byte[]) array;
                wrappedArray = new Byte[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            } else if (componentType == short.class) {
                short[] originalArray = (short[]) array;
                wrappedArray = new Short[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            } else if (componentType == int.class) {
                int[] originalArray = (int[]) array;
                wrappedArray = new Integer[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            } else if (componentType == long.class) {
                long[] originalArray = (long[]) array;
                wrappedArray = new Long[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            } else if (componentType == float.class) {
                float[] originalArray = (float[]) array;
                wrappedArray = new Float[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            } else if (componentType == double.class) {
                double[] originalArray = (double[]) array;
                wrappedArray = new Double[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            } else if (componentType == boolean.class) {
                boolean[] originalArray = (boolean[]) array;
                wrappedArray = new Boolean[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            } else if (componentType == char.class) {
                char[] originalArray = (char[]) array;
                wrappedArray = new Character[originalArray.length];
                for (int i = 0; i < originalArray.length; i++) {
                    wrappedArray[i] = originalArray[i];
                }
            }
            return wrappedArray;
        }
        return (Object[]) array;
    }
}
