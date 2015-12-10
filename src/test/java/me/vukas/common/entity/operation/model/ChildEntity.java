package me.vukas.common.entity.operation.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChildEntity extends BaseEntity {
    protected int baseEntityProtectedInt;
    public int baseEntityPublicInt;

    private int commonInt;
    private int childEntityInt;
    protected int childEntityProtectedInt;
    public int childEntityPublicInt;

    protected String baseEntityProtectedString;
    public String baseEntityPublicString;

    private String commonString;
    private String childEntityString;
    protected String childEntityProtectedString;
    public String childEntityPublicString;

    protected Enumeration baseEntityProtectedEnumeration;
    public Enumeration baseEntityPublicEnumeration;

    private Enumeration commonEnumeration;
    private Enumeration childEntityEnumeration;
    protected Enumeration childEntityProtectedEnumeration;
    public Enumeration childEntityPublicEnumeration;

    protected static int baseEntityProtectedStaticInt;
    public static int baseEntityPublicStaticInt;

    private static int commonStaticInt;
    private static int childEntityStaticInt;
    protected static int childEntityProtectedStaticInt;
    public static int childEntityPublicStaticInt;

    protected static String baseEntityProtectedStaticString;
    public static String baseEntityPublicStaticString;

    private static String commonStaticString;
    private static String childEntityStaticString;
    protected static String childEntityProtectedStaticString;
    public static String childEntityPublicStaticString;

    protected int[] baseEntityProtectedIntArray;
    public int[] baseEntityPublicIntArray;

    private int[] commonIntArray;
    private int[] childEntityIntArray;
    protected int[] childEntityProtectedIntArray;
    public int[] childEntityPublicIntArray;

    protected String[] baseEntityProtectedStringArray;
    public String[] baseEntityPublicStringArray;

    private String[] commonStringArray;
    private String[] childEntityStringArray;
    protected String[] childEntityProtectedStringArray;
    public String[] childEntityPublicStringArray;

    protected List<String> baseEntityProtectedStringList;
    public List<String> baseEntityPublicStringList;

    private List<String> commonStringList;
    private List<String> childEntityStringList;
    protected List<String> childEntityProtectedStringList;
    public List<String> childEntityPublicStringList;

    protected Set<String> baseEntityProtectedStringSet;
    public Set<String> baseEntityPublicStringSet;

    private Set<String> commonStringSet;
    private Set<String> childEntityStringSet;
    protected Set<String> childEntityProtectedStringSet;
    public Set<String> childEntityPublicStringSet;

    protected Map<String, String> baseEntityProtectedStringMap;
    public Map<String, String> baseEntityPublicStringMap;

    private Map<String, String> commonStringMap;
    private Map<String, String> childEntityStringMap;
    protected Map<String, String> childEntityProtectedStringMap;
    public Map<String, String> childEntityPublicStringMap;

    private ChildEntity parent;

    @Override
    public void base() {
    }
}
