package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class NodeBranch  extends Node {
    NodeBranch(ChunkCoordinate coordinate) {
        super(coordinate);
    }

    @Override
    public TypeNode getType() {
        return TypeNode.BRANCH;
    }

}
