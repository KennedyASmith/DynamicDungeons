package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeCorridor extends Node {
    NodeCorridor(ChunkCoordinate coordinate) {
        super(coordinate);
    }
    @Override
    public TypeNode getType() {
        return TypeNode.ROOM;
    }
}
