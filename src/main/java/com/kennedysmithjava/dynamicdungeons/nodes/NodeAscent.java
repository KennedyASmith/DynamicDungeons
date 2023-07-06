package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

public class NodeAscent extends Node {

    NodeAscent(ChunkCoordinate coordinate, Direction facing) {
        super(coordinate, facing);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.ASCENT;
    }

    @Override
    public String getSymbol() {
        return "&aA";
    }
}
