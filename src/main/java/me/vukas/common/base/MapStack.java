package me.vukas.common.base;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Stack;

public class MapStack<K, V> extends Stack<Map.Entry<K, V>> {
    public Map.Entry<K, V> push(K key, V value) {
        return super.push(new AbstractMap.SimpleEntry<K, V>(key, value));
    }

    public boolean containsKeyAndValuePair(K key, V value){
        for(Map.Entry<K, V> entry : this){
            if(entry.getKey().equals(key) && entry.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }
}
