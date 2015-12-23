package me.vukas.common.entity.operation.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

    public ChildEntity(boolean init){
        super(init);
    }

    public ChildEntity(){
        this(ThreadLocalRandom.current().nextInt(1, 11));
    }

    public ChildEntity(int sequenceNumber){
        super(ThreadLocalRandom.current().nextInt(1, 11));
        this.baseEntityProtectedInt = sequenceNumber;
        this.baseEntityPublicInt = sequenceNumber;
        this.commonInt = sequenceNumber;
        this.childEntityInt = sequenceNumber;
        this.childEntityProtectedInt = sequenceNumber;
        this.childEntityPublicInt = sequenceNumber;
        this.baseEntityProtectedString = "baseEntityProtectedString" + sequenceNumber;
        this.baseEntityPublicString = "baseEntityPublicString" + sequenceNumber;

        this.commonString = "commonString" + sequenceNumber;
        this.childEntityString = "childEntityString" +sequenceNumber;
        this.childEntityProtectedString = "childEntityProtectedString" + sequenceNumber;
        this.childEntityPublicString = "childEntityPublicString" + sequenceNumber;

        this.baseEntityProtectedEnumeration = Enumeration.ENUM1;
        this.baseEntityPublicEnumeration = Enumeration.ENUM2;

        this.commonEnumeration = Enumeration.ENUM1;
        this.childEntityEnumeration = Enumeration.ENUM2;
        this.childEntityProtectedEnumeration = Enumeration.ENUM1;
        this.childEntityPublicEnumeration = Enumeration.ENUM2;

        baseEntityProtectedStaticInt = sequenceNumber;
        baseEntityPublicStaticInt = sequenceNumber;

        commonStaticInt = sequenceNumber;
        childEntityStaticInt = sequenceNumber;

        childEntityProtectedStaticInt = sequenceNumber;
        childEntityPublicStaticInt = sequenceNumber;

        baseEntityProtectedStaticString = "baseEntityProtectedStaticString" + sequenceNumber;
        baseEntityPublicStaticString = "baseEntityPublicStaticString" + sequenceNumber;

        commonStaticString = "commonStaticString" + sequenceNumber;
        childEntityStaticString = "childEntityStaticString" + sequenceNumber;
        childEntityProtectedStaticString = "childEntityProtectedStaticString" + sequenceNumber;
        childEntityPublicStaticString = "childEntityPublicStaticString" + sequenceNumber;

        this.baseEntityProtectedIntArray = new int[sequenceNumber];
        this.baseEntityPublicIntArray = new int[sequenceNumber];
        this.commonIntArray = new int[sequenceNumber];
        this.childEntityIntArray = new int[sequenceNumber];
        this.childEntityProtectedIntArray = new int[sequenceNumber];
        this.childEntityPublicIntArray = new int[sequenceNumber];

        this.baseEntityProtectedStringArray = new String[sequenceNumber];
        this.baseEntityPublicStringArray = new String[sequenceNumber];
        this.commonStringArray = new String[sequenceNumber];
        this.childEntityStringArray = new String[sequenceNumber];
        this.childEntityProtectedStringArray = new String[sequenceNumber];
        this.childEntityPublicStringArray = new String[sequenceNumber];

        this.baseEntityProtectedStringList = new ArrayList<String>();
        this.baseEntityPublicStringList = new ArrayList<String>();
        this.commonStringList = new ArrayList<String>();
        this.childEntityStringList = new ArrayList<String>();
        this.childEntityProtectedStringList = new ArrayList<String>();
        this.childEntityPublicStringList = new ArrayList<String>();

        this.baseEntityProtectedStringSet = new HashSet<String>();
        this.baseEntityPublicStringSet = new HashSet<String>();
        this.commonStringSet = new HashSet<String>();
        this.childEntityStringSet = new HashSet<String>();
        this.childEntityProtectedStringSet = new HashSet<String>();
        this.childEntityPublicStringSet = new HashSet<String>();

        this.baseEntityProtectedStringMap = new HashMap<String, String>();
        this.baseEntityPublicStringMap = new HashMap<String, String>();
        this.commonStringMap = new HashMap<String, String>();
        this.childEntityStringMap = new HashMap<String, String>();
        this.childEntityProtectedStringMap = new HashMap<String, String>();
        this.childEntityPublicStringMap = new HashMap<String, String>();

        for(int i=0; i<sequenceNumber; i++){
            this.baseEntityProtectedIntArray[i] = i;
            this.baseEntityPublicIntArray[i] = i;
            this.commonIntArray[i] = i;
            this.childEntityIntArray[i] = i;
            this.childEntityProtectedIntArray[i] = i;
            this.childEntityPublicIntArray[i] = i;

            this.baseEntityProtectedStringArray[i] = "baseEntityProtectedStringArray" + i;
            this.baseEntityPublicStringArray[i] = "baseEntityPublicStringArray" + i;
            this.commonStringArray[i] = "commonStringArray" + i;
            this.childEntityStringArray[i] = "childEntityStringArray" + i;
            this.childEntityProtectedStringArray[i] = "childEntityProtectedStringArray" + i;
            this.childEntityPublicStringArray[i] = "childEntityPublicStringArray" + i;

            this.baseEntityProtectedStringList.add("baseEntityProtectedStringList" + i);
            this.baseEntityPublicStringList.add("baseEntityPublicStringList" + i);
            this.commonStringList.add("commonStringList" + i);
            this.childEntityStringList.add("childEntityStringList" + i);
            this.childEntityProtectedStringList.add("childEntityProtectedStringList" + i);
            this.childEntityPublicStringList.add("childEntityPublicStringList" + i);

            this.baseEntityProtectedStringSet.add("baseEntityProtectedStringSet" + i);
            this.baseEntityPublicStringSet.add("baseEntityPublicStringSet" + i);
            this.commonStringSet.add("commonStringSet" + i);
            this.childEntityStringSet.add("childEntityStringSet" + i);
            this.childEntityProtectedStringSet.add("childEntityProtectedStringSet" + i);
            this.childEntityPublicStringSet.add("childEntityPublicStringSet" + i);

            this.baseEntityProtectedStringMap.put("baseEntityProtectedStringMapKey"+i, "baseEntityProtectedStringMapValue"+i);
            this.baseEntityPublicStringMap.put("baseEntityProtectedStringMapKey"+i, "baseEntityProtectedStringMapValue"+i);
            this.commonStringMap.put("commonStringMapKey"+i, "commonStringMapValue"+i);
            this.childEntityStringMap.put("childEntityStringMapKey"+i, "childEntityStringMapValue"+i);
            this.childEntityProtectedStringMap.put("childEntityProtectedStringMapKey"+i, "childEntityProtectedStringMapValue"+i);
            this.childEntityPublicStringMap.put("childEntityPublicStringMapKey"+i, "childEntityPublicStringMapValue"+i);
        }
    }

    @Override
    public void base() {
    }
}
