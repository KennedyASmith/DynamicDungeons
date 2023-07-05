package com.kennedysmithjava.dynamicdungeons.util;

import com.kennedysmithjava.dynamicdungeons.nodes.TypeNode;

public class NodeTypeRecord {


    private final TypeNode lastNodeType;
    private int count;

    public NodeTypeRecord(TypeNode lastNodeType, int count) {
        this.lastNodeType = lastNodeType;
        this.count = count;
    }

    public TypeNode getLastNodeType() {
        return lastNodeType;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount(){
        count += 1;
    }
}
