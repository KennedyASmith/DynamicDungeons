package com.kennedysmithjava.dynamicdungeons.util;

public enum Direction {
    WEST,
    EAST,
    NORTH,
    SOUTH;

    public Direction invert(){
        switch(this){
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
            default:
                return SOUTH;
        }
    }

    public Direction getRight(){
        switch(this){
            case SOUTH:
                return WEST;
            case EAST:
                return SOUTH;
            case WEST:
                return NORTH;
            default:
                return EAST;
        }
    }

    public Direction getLeft(){
        switch(this){
            case SOUTH:
                return EAST;
            case EAST:
                return NORTH;
            case WEST:
                return SOUTH;
            default:
                return WEST;
        }
    }
}
