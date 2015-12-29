package me.vukas.common.base;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
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

    public static Object unwrapCollectionOrMapOrPrimitiveArray(Object input, Class outputType){
        if(outputType.isArray()){
            Class componentType = outputType.getComponentType();
            if(componentType.isPrimitive()) {
                return unwrap((Object[])input);
            }
        }
        if(Collection.class.isAssignableFrom(outputType)){
            Collection newCollection = null;
            try {
                newCollection = (Collection)outputType.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            Collections.addAll(newCollection, (Object[]) input);
            return newCollection;
        }
        else if(Map.class.isAssignableFrom(outputType)){
            Map newMap = null;
            try {
                newMap = (Map)outputType.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            for(Object entry : (Object[])input){
                Map.Entry mapEntry = (Map.Entry)entry;
                newMap.put(mapEntry.getKey(), mapEntry.getValue());
            }
            return newMap;
        }
        return input;   //if needs no unwrapping, return original
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

    //TODO: CHECK logic
    public static void insert(Object array, int index, Object element){
        Class componentType = array.getClass().getComponentType();
        if(componentType.isPrimitive()){
            if (byte.class.isAssignableFrom(componentType)) {
                ((byte[])array)[index] = (Byte)element;
            } else if (short.class.isAssignableFrom(componentType)) {
                ((short[])array)[index] = (Short)element;
            } else if (int.class.isAssignableFrom(componentType)) {
                ((int[])array)[index] = (Integer)element;
            } else if (long.class.isAssignableFrom(componentType)) {
                ((long[])array)[index] = (Long)element;
            } else if (float.class.isAssignableFrom(componentType)) {
                ((float[])array)[index] = (Float)element;
            } else if (double.class.isAssignableFrom(componentType)) {
                ((double[])array)[index] = (Double)element;
            } else if (boolean.class.isAssignableFrom(componentType)) {
                ((boolean[])array)[index] = (Boolean)element;
            } else if (char.class.isAssignableFrom(componentType)) {
                ((char[])array)[index] = (Character)element;
            }
        }
        else {
            ((Object[]) array)[index] = element;
        }
    }

    public static Object unwrap(Object[] array){
        if(array == null){
            return null;
        }
        int arrayLength = 0;
        for(int i=0; i<array.length; i++){
            if(array[i]!=null){
                arrayLength++;
            }
        }
        if(array instanceof Byte[]){
            byte[] unwrappedArray = new byte[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Byte)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        else if(array instanceof Short[]){
            short[] unwrappedArray = new short[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Short)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        else if(array instanceof Integer[]){
            int[] unwrappedArray = new int[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Integer)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        else if(array instanceof Long[]){
            long[] unwrappedArray = new long[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Long)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        else if(array instanceof Float[]){
            float[] unwrappedArray = new float[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Float)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        else if(array instanceof Double[]){
            double[] unwrappedArray = new double[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Double)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        else if(array instanceof Boolean[]){
            boolean[] unwrappedArray = new boolean[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Boolean)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        else if(array instanceof Character[]){
            char[] unwrappedArray = new char[arrayLength];
            int unwrappedIndex = 0;
            for(int i = 0; i < array.length; i++){
                if(array[i]!=null){
                    unwrappedArray[unwrappedIndex] = (Character)array[i];
                    unwrappedIndex++;
                }
            }
            return unwrappedArray;
        }
        return array;
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
