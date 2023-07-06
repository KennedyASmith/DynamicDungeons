package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

public class NodeEnd extends Node {
    public NodeEnd(ChunkCoordinate coordinate, Direction facing) {
        super(coordinate, facing);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.DEAD_END;
    }

    @Override
    public String getSymbol() {
        return "&c⛋";
    }
}
