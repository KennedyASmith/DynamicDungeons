package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeRightCorner  extends Node {
    public NodeRightCorner(ChunkCoordinate coordinate) {
        super(coordinate);
    }
    @Override
    public TypeNode getType() {
        return TypeNode.RIGHT_CORNER;
    }
}
