package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

public class NodeCorridor extends Node {

    NodeCorridor(ChunkCoordinate coordinate, Direction facing) {
        super(coordinate, facing);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.ROOM;
    }

    @Override
    public String getSymbol() {
        return "&f⃞";
    }
}
