package me.vukas.common.entity.operation.model;

import java.util.*;

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

    private GrandChildEntity parent1;
    private GrandChildEntity parent2;

    private List<GrandChildEntity> parents;

    public void setParent1(GrandChildEntity parent1) {
        this.parent1 = parent1;
    }

    public void setParent2(GrandChildEntity parent2) {
        this.parent2 = parent2;
    }

    public void addParent(GrandChildEntity parent){
        this.parents.add(parent);
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

        this.parents = new ArrayList<GrandChildEntity>();
    }
}
