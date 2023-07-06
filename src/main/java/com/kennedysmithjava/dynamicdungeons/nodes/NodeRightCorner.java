package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

public class NodeRightCorner  extends Node {
    public NodeRightCorner(ChunkCoordinate coordinate, Direction facing) {
        super(coordinate, facing);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.RIGHT_CORNER;
    }

    @Override
    public String getSymbol() {
       /* return switch (this.getDirection()){
            case NORTH -> "&6&l↓";
            case EAST -> "&6&l→";
            case SOUTH -> "&6&l↑";
            case WEST -> "&6&l←";
        };*/
        return "⧄";
    }
}
