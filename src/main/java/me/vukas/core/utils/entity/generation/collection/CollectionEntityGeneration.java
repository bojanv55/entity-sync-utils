package me.vukas.core.utils.entity.generation.collection;

import me.vukas.core.utils.entity.EntityGeneration;
import me.vukas.core.utils.entity.element.Element;
import me.vukas.core.utils.entity.key.Key;

import java.util.Collection;

public class CollectionEntityGeneration extends EntityGeneration<Collection> {
    @Override
    public <N> Element diff(Collection original, Collection revised, N elementName, Class fieldType, Class containerType, Key<N, Collection> key) {
        return null;
    }

    @Override
    public <N> Key generateKey(N elementName, Class elementType, Class containerType, Collection value) {
        return null;
    }

    @Override
    public boolean areEqual(Collection entity1, Collection entity2, Class fieldType) {
        return false;
    }
}
