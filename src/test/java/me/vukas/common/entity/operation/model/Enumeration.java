package me.vukas.common.entity.operation.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public enum Enumeration implements Base {
    ENUM1,
    ENUM2;

    private int commonInt;
    private String commonString;
    private static int commonStaticInt;
    private static String commonStaticString;
    private int[] commonIntArray;
    private String[] commonStringArray;
    private List<String> commonStringList;
    private Set<String> commonStringSet;
    private Map<String, String> commonStringMap;

    @Override
    public void base() {
    }
}
