package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeLeftCorner  extends Node {
    public NodeLeftCorner(ChunkCoordinate coordinate) {
        super(coordinate);
    }
    @Override
    public TypeNode getType() {
        return TypeNode.LEFT_CORNER;
    }
}
