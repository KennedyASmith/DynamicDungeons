package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeStraight extends Node {
    public NodeStraight(ChunkCoordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.STRAIGHT;
    }
}
