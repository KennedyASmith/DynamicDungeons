package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

public class NodeStraight extends Node {

    public NodeStraight(ChunkCoordinate coordinate, Direction facing) {
        super(coordinate, facing);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.STRAIGHT;
    }

    @Override
    public String getSymbol() {
       /* return switch (this.getDirection()) {
            case EAST -> "&7↓";
            case NORTH -> "&7←";
            case SOUTH -> "&7→";
            case WEST -> "&7↑";
        };*/
        return "⧇";
    }
}
