package com.kennedysmithjava.dynamicdungeons.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum TypeBranch {
    LEFT,
    RIGHT,
    TEE,
    CROSS;

    public Map<Direction, ChunkCoordinate> getDirections(Direction facing, ChunkCoordinate current) {
        return switch (this) {
            case LEFT -> Util.map(
                    facing.getLeft(), facing.getLeft().getLeftCoordinate(current),
                    facing, facing.getFrontCoordinate(current)
            );
            case RIGHT -> Util.map(
                    facing.getRight(), facing.getRight().getRightCoordinate(current),
                    facing, facing.getFrontCoordinate(current)
            );
            case TEE -> Util.map(
                    facing.getLeft(), facing.getLeft().getLeftCoordinate(current),
                    facing.getRight(), facing.getRight().getRightCoordinate(current)
            );
            case CROSS -> Util.map(
                    facing.getLeft(), facing.getLeft().getLeftCoordinate(current),
                    facing.getRight(), facing.getRight().getRightCoordinate(current),
                    facing, facing.getFrontCoordinate(current)
            );
        };
    }
}

/*
    Cross
    [0][=][0]
    [=][=][=]
    [0][x][0]

    Left
    [0][=][0]
    [=][=][0]
    [0][x][0]

    Right
    [0][=][0]
    [0][=][=]
    [0][x][0]

    Tee
    [0][0][0]
    [=][=][=]
    [0][x][0]

 */