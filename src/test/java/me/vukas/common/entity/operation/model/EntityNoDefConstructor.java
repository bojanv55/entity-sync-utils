package me.vukas.common.entity.operation.model;

public class EntityNoDefConstructor {
    private int id;
    private GrandChildEntity child;

    public EntityNoDefConstructor(int id, GrandChildEntity child){
        this.id = id;
        this.child = child;
    }
}
