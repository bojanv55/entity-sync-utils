package me.vukas.common.entity.generation.collection;

import me.vukas.common.entity.EntityGeneration;
import me.vukas.common.entity.element.Element;
import me.vukas.common.entity.key.Key;

import java.util.Collection;

public class CollectionEntityGeneration extends EntityGeneration<Collection> {
    @Override
    public <N> Element<N, Collection> diff(Collection original, Collection revised, N elementName, Class fieldType, Class containerType, Key<N, Collection> key) {
        return null;
    }

    @Override
    public <N> Key<N, Collection> generateKey(N elementName, Class elementType, Class containerType, Collection value) {
        return null;
    }

    @Override
    public boolean compare(Collection entity1, Collection entity2, Class fieldType) {
        return false;
    }
}
