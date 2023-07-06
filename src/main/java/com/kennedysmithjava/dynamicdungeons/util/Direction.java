package com.kennedysmithjava.dynamicdungeons.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Direction {
    WEST,
    EAST,
    NORTH,
    SOUTH;

    public Direction invert(){
        return switch (this) {
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            default -> SOUTH;
        };
    }

    public Direction getRight(){
        return switch (this) {
            case SOUTH -> WEST;
            case EAST -> SOUTH;
            case WEST -> NORTH;
            default -> EAST;
        };
    }

    public Direction getLeft(){
        return switch (this) {
            case SOUTH -> EAST;
            case EAST -> NORTH;
            case WEST -> SOUTH;
            default -> WEST;
        };
    }

    public static List<Direction> allExcept(Direction direction){
        return Arrays.stream(values()).filter(value -> !value.equals(direction)).collect(Collectors.toList());
    }

    public ChunkCoordinate getLeftCoordinate(ChunkCoordinate current){
        int currentX = current.getX();
        int currentY = current.getY();
        int currentZ = current.getZ();
        return switch (this) {
            case NORTH -> new ChunkCoordinate(currentX - 1, currentY, currentZ);
            case WEST -> new ChunkCoordinate(currentX, currentY, currentZ + 1);
            case EAST -> new ChunkCoordinate(currentX, currentY, currentZ - 1);
            default -> new ChunkCoordinate(currentX + 1, currentY, currentZ);
        };
    }

    public ChunkCoordinate getRightCoordinate(ChunkCoordinate current){
        int currentX = current.getX();
        int currentY = current.getY();
        int currentZ = current.getZ();
        return switch (this) {
            case NORTH -> new ChunkCoordinate(currentX + 1, currentY, currentZ);
            case WEST -> new ChunkCoordinate(currentX, currentY, currentZ - 1);
            case EAST -> new ChunkCoordinate(currentX, currentY, currentZ + 1);
            default -> new ChunkCoordinate(currentX - 1, currentY, currentZ);
        };
    }

    public ChunkCoordinate getFrontCoordinate(ChunkCoordinate current){
        int currentX = current.getX();
        int currentY = current.getY();
        int currentZ = current.getZ();
        return switch (this) {
            case NORTH -> new ChunkCoordinate(currentX, currentY, currentZ - 1);
            case WEST -> new ChunkCoordinate(currentX - 1, currentY, currentZ);
            case EAST -> new ChunkCoordinate(currentX + 1, currentY, currentZ);
            default -> new ChunkCoordinate(currentX, currentY, currentZ + 1);
        };
    }
}
