package me.vukas.common.entity.operation.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

    public BaseEntity(boolean init){}

    public BaseEntity(){
        this(1);
    }

    public BaseEntity(int sequenceNumber){
        this.baseEntityProtectedInt = sequenceNumber;
        this.baseEntityPublicInt = sequenceNumber;
        this.commonInt = sequenceNumber;
        this.baseEntityInt = sequenceNumber;

        this.baseEntityProtectedString = "baseEntityProtectedString" + sequenceNumber;
        this.baseEntityPublicString = "baseEntityPublicString" + sequenceNumber;

        this.commonString = "commonString" + sequenceNumber;
        this.baseEntityString = "baseEntityString" + sequenceNumber;

        this.baseEntityProtectedEnumeration = Enumeration.ENUM2;
        this.baseEntityPublicEnumeration = Enumeration.ENUM1;

        this.commonEnumeration = Enumeration.ENUM2;
        this.baseEntityEnumeration = Enumeration.ENUM1;

        baseEntityProtectedStaticInt = 1;
        baseEntityPublicStaticInt = 1;

        commonStaticInt = 1;
        baseEntityStaticInt = 1;

        baseEntityProtectedStaticString = "baseEntityProtectedStaticString" + 1;
        baseEntityPublicStaticString = "baseEntityPublicStaticString" + 1;

        commonStaticString = "commonStaticString" + 1;
        baseEntityStaticString = "baseEntityStaticString" + 1;

        this.baseEntityProtectedIntArray = new int[sequenceNumber];
        this.baseEntityPublicIntArray = new int[sequenceNumber];
        this.commonIntArray = new int[sequenceNumber];
        this.baseEntityIntArray = new int[sequenceNumber];

        this.baseEntityProtectedStringArray = new String[sequenceNumber];
        this.baseEntityPublicStringArray = new String[sequenceNumber];
        this.commonStringArray = new String[sequenceNumber];
        this.baseEntityStringArray = new String[sequenceNumber];

        this.baseEntityProtectedStringList = new ArrayList<String>();
        this.baseEntityPublicStringList = new ArrayList<String>();
        this.commonStringList = new ArrayList<String>();
        this.baseEntityStringList = new ArrayList<String>();

        this.baseEntityProtectedStringSet = new HashSet<String>();
        this.baseEntityPublicStringSet = new HashSet<String>();
        this.commonStringSet = new HashSet<String>();
        this.baseEntityStringSet = new HashSet<String>();

        this.baseEntityProtectedStringMap = new HashMap<String, String>();
        this.baseEntityPublicStringMap = new HashMap<String, String>();
        this.commonStringMap = new HashMap<String, String>();
        this.baseEntityStringMap = new HashMap<String, String>();

        for(int i=0; i<sequenceNumber; i++){
            this.baseEntityProtectedIntArray[i] = i;
            this.baseEntityPublicIntArray[i] = i;
            this.commonIntArray[i] = i;

            this.baseEntityProtectedStringArray[i] = "baseEntityProtectedStringArray" + i;
            this.baseEntityPublicStringArray[i] = "baseEntityPublicStringArray" + i;
            this.commonStringArray[i] = "commonStringArray" + i;
            this.baseEntityStringArray[i] = "baseEntityStringArray" + i;

            this.baseEntityProtectedStringList.add("baseEntityProtectedStringList" + i);
            this.baseEntityPublicStringList.add("baseEntityPublicStringList" + i);
            this.commonStringList.add("commonStringList" + i);
            this.baseEntityStringList.add("baseEntityStringList" + i);

            this.baseEntityProtectedStringSet.add("baseEntityProtectedStringSet" + i);
            this.baseEntityPublicStringSet.add("baseEntityPublicStringSet" + i);
            this.commonStringSet.add("commonStringSet" + i);
            this.baseEntityStringSet.add("baseEntityStringSet" + i);

            this.baseEntityProtectedStringMap.put("baseEntityProtectedStringMapKey" + i, "baseEntityProtectedStringMapValue" + i);
            this.baseEntityPublicStringMap.put("baseEntityProtectedStringMapKey"+i, "baseEntityProtectedStringMapValue"+i);
            this.commonStringMap.put("commonStringMapKey"+i, "commonStringMapValue"+i);
            this.baseEntityStringMap.put("baseEntityStringMapKey"+i, "baseEntityStringMapValue"+i);
        }
    }
}
