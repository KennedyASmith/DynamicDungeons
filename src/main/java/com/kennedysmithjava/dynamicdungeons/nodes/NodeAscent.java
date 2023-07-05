package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeAscent extends Node {
    NodeAscent(ChunkCoordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.ASCENT;
    }
}
