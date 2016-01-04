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

    private GrandChildEntity[] parentsArray;
    private List<GrandChildEntity> parentsList;
    private Set<GrandChildEntity> parentsSet;
    private Map<GrandChildEntity, GrandChildEntity> parentsMap;

    private Base parentInterface;
    private Base[] parentInterfaceArray;

    private BaseEntity parentAbstract;
    private BaseEntity[] parentAbstractArray;

    public void setParent1(GrandChildEntity parent1) {
        this.parent1 = parent1;
    }

    public void setParent2(GrandChildEntity parent2) {
        this.parent2 = parent2;
    }

    public void setParentInterface(Base parentInterface) {
        this.parentInterface = parentInterface;
    }

    public void setParentAbstract(BaseEntity parentAbstract) {
        this.parentAbstract = parentAbstract;
    }

    public void addParentInArray(int index, GrandChildEntity parent){
        this.parentsArray[index] = parent;
    }

    public void addParentInList(GrandChildEntity parent){
        this.parentsList.add(parent);
    }

    public void addParentInSet(GrandChildEntity parent){
        this.parentsSet.add(parent);
    }

    public void addParentsInMap(GrandChildEntity key, GrandChildEntity value){
        this.parentsMap.put(key, value);
    }

    public void addParentInInterfaceArray(int index, Base parent){
        this.parentInterfaceArray[index] = parent;
    }

    public void addParentInAbstractArray(int index, BaseEntity parent){
        this.parentAbstractArray[index] = parent;
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

        this.parentsArray = new GrandChildEntity[sequenceNumber];
        this.parentsList = new ArrayList<GrandChildEntity>();
        this.parentsSet = new HashSet<GrandChildEntity>();
        this.parentsMap = new HashMap<GrandChildEntity, GrandChildEntity>();

        this.parentInterfaceArray = new Base[sequenceNumber];
        this.parentAbstractArray = new BaseEntity[sequenceNumber];
    }
}
