package me.vukas.core.utils.entity.generation.map;

import me.vukas.core.utils.entity.EntityGeneration;
import me.vukas.core.utils.entity.element.Element;
import me.vukas.core.utils.entity.key.Key;

import java.util.Map;

public class MapEntryEntityGeneration extends EntityGeneration<Map.Entry> {
    @Override
    public <N> Element diff(Map.Entry original, Map.Entry revised, N elementName, Class fieldType, Class containerType, Key<N, Map.Entry> key) {
        return null;
    }

    @Override
    public <N> Key generateKey(N elementName, Class elementType, Class containerType, Map.Entry value) {
        return null;
    }

    @Override
    public boolean areEqual(Map.Entry entity1, Map.Entry entity2, Class fieldType) {
        return false;
    }
}
