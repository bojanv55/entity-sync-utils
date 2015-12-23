package me.vukas.common.entity.operation.model;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class EnumerationImpl implements Base {
    private int commonInt;
    private String commonString;
    private static int commonStaticInt = 2;
    private static String commonStaticString = "commonStaticString2";
    private int[] commonIntArray;
    private String[] commonStringArray;
    private List<String> commonStringList;
    private Set<String> commonStringSet;
    private Map<String, String> commonStringMap;

    EnumerationImpl(){
        this(1);
    }

    EnumerationImpl(int sequenceNumber){
        this.commonInt = sequenceNumber;
        this.commonString = "commonString" + sequenceNumber;
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

    @Override
    public void base() {

    }
}
