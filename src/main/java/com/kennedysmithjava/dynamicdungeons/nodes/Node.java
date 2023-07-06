package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

public abstract class Node {


    private final ChunkCoordinate coordinate;
    private final Direction direction;
    private String color = "&7";

    Node(ChunkCoordinate coordinate, Direction facing){
        this.direction = facing;
        this.coordinate = coordinate;
    }

    public ChunkCoordinate getCoordinate(){
        return coordinate;
    }

    public abstract TypeNode getType();

    public abstract String getSymbol();

    public String getColoredSymbol(){
        return color + getSymbol();
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Direction getDirection() {
        return direction;
    }
}
