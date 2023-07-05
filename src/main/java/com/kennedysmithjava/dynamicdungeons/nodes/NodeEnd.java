package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeEnd extends Node {
    public NodeEnd(ChunkCoordinate coordinate) {
        super(coordinate);
    }
    @Override
    public TypeNode getType() {
        return TypeNode.DEAD_END;
    }
}
