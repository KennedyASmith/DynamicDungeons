package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public abstract class Node {


    private final ChunkCoordinate coordinate;

    Node(ChunkCoordinate coordinate){
        this.coordinate = coordinate;
    }

    public ChunkCoordinate getCoordinate(){
        return coordinate;
    }

    public abstract TypeNode getType();
}
