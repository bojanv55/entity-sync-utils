package me.vukas.common.entity.operation.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GrandChildEntity extends ChildEntity {
    private int commonInt;
    private String commonString;
    private static int commonStaticInt;
    private static String commonStaticString;
    private int[] commonIntArray;
    private String[] commonStringArray;
    private List<String> commonStringList;
    private Set<String> commonStringSet;
    private Map<String, String> commonStringMap;

    private GrandChildEntity parent;

    public void setParent(GrandChildEntity parent) {
        this.parent = parent;
    }

    public GrandChildEntity(boolean init){
        super(init);
    }

    public GrandChildEntity(){
        this(1);
    }

    public GrandChildEntity(int sequenceNumber){
        super(sequenceNumber+1);
        this.commonInt = sequenceNumber;
        this.commonString = "commonString" + sequenceNumber;
        commonStaticInt = 3;
        commonStaticString = "commonStaticString" + 3;
        this.commonIntArray = new int[sequenceNumber];
        this.commonStringArray = new String[sequenceNumber];
        this.commonStringList = new ArrayList<String>();
        this.commonStringSet = new HashSet<String>();
        this.commonStringMap = new HashMap<String, String>();
        for(int i=0; i<sequenceNumber; i++){
            this.commonIntArray[i] = i;
            this.commonStringArray[i] = "commonStringArray" + i;
            this.commonStringList.add("commonStringList" + i);
            this.commonStringSet.add("commonStringSet" + i);
            this.commonStringMap.put("commonStringMapKey"+i, "commonStringMapValue"+i);
        }
    }
}
