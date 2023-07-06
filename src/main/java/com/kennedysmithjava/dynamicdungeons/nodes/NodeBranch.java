package com.kennedysmithjava.dynamicdungeons.nodes;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;
import com.kennedysmithjava.dynamicdungeons.util.TypeBranch;

public class NodeBranch  extends Node {
    private final TypeBranch branchType;

    public NodeBranch(ChunkCoordinate coordinate, Direction facing, TypeBranch branchType) {
        super(coordinate, facing);
        this.branchType = branchType;
    }

    @Override
    public TypeNode getType() {
        return TypeNode.BRANCH;
    }

    @Override
    public String getSymbol() {/*
        return switch (branchType) {
            case TEE -> switch (this.getDirection()) {
                case NORTH -> "&7&l┣";
                case WEST -> "&7&l┳";
                case EAST -> "&7&l┻";
                case SOUTH -> "&7&l┫";
            };
            case CROSS -> switch (this.getDirection()) {
                case NORTH -> "&7&l╉";
                case WEST -> "&7&l╇";
                case EAST -> "&7&l╈";
                case SOUTH -> "&7&l╊";
            };
            case LEFT -> switch (this.getDirection()) {
                case NORTH -> "&7&l┱";
                case WEST -> "&7&l┩";
                case EAST -> "&7&l┢";
                case SOUTH -> "&7&l┺";
            };
            case RIGHT -> switch (this.getDirection()) {
                case NORTH -> "&7&l┹";
                case WEST -> "&7&l┡";
                case EAST -> "&7&l┪";
                case SOUTH -> "&7&l┲";
            };
        };*/
        return "&d⛋";
    }
}
