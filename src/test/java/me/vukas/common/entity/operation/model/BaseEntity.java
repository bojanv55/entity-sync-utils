package me.vukas.common.entity.operation.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class BaseEntity implements Base {
    private int commonInt;
    private int baseEntityInt;
    protected int baseEntityProtectedInt;
    public int baseEntityPublicInt;

    private String commonString;
    private String baseEntityString;
    protected String baseEntityProtectedString;
    public String baseEntityPublicString;

    private Enumeration commonEnumeration;
    private Enumeration baseEntityEnumeration;
    protected Enumeration baseEntityProtectedEnumeration;
    public Enumeration baseEntityPublicEnumeration;

    private static int commonStaticInt;
    private static int baseEntityStaticInt;
    protected static int baseEntityProtectedStaticInt;
    public static int baseEntityPublicStaticInt;

    private static String commonStaticString;
    private static String baseEntityStaticString;
    protected static String baseEntityProtectedStaticString;
    public static String baseEntityPublicStaticString;

    private int[] commonIntArray;
    private int[] baseEntityIntArray;
    protected int[] baseEntityProtectedIntArray;
    public int[] baseEntityPublicIntArray;

    private String[] commonStringArray;
    private String[] baseEntityStringArray;
    protected String[] baseEntityProtectedStringArray;
    public String[] baseEntityPublicStringArray;

    private List<String> commonStringList;
    private List<String> baseEntityStringList;
    protected List<String> baseEntityProtectedStringList;
    public List<String> baseEntityPublicStringList;

    private Set<String> commonStringSet;
    private Set<String> baseEntityStringSet;
    protected Set<String> baseEntityProtectedStringSet;
    public Set<String> baseEntityPublicStringSet;

    private Map<String, String> commonStringMap;
    private Map<String, String> baseEntityStringMap;
    protected Map<String, String> baseEntityProtectedStringMap;
    public Map<String, String> baseEntityPublicStringMap;
}
