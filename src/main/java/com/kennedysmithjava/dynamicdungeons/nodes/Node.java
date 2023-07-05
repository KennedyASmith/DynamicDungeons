package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class Node {


    private final ChunkCoordinate coordinate;

    Node(ChunkCoordinate coordinate){
        this.coordinate = coordinate;
    }

    public ChunkCoordinate getCoordinate(){
        return coordinate;
    }
}
