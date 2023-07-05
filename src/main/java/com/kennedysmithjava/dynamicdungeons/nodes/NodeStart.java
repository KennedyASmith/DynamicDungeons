package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeStart extends Node {
    public NodeStart(ChunkCoordinate coordinate) {
        super(coordinate);
    }
    @Override
    public TypeNode getType() {
        return TypeNode.START;
    }
}
