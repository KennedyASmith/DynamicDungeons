package com.kennedysmithjava.dynamicdungeons.util;

public enum TypeBranch {
    LEFT,
    RIGHT,
    TEE,
    CROSS,
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