package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

public class NodeDescent extends Node {
    NodeDescent(ChunkCoordinate coordinate, Direction facing) {
        super(coordinate, facing);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.DESCENT;
    }

    @Override
    public String getSymbol() {
        return "&aD";
    }
}
