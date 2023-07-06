package com.kennedysmithjava.dynamicdungeons.templates;

import com.kennedysmithjava.dynamicdungeons.DungeonFormat;
import com.kennedysmithjava.dynamicdungeons.DungeonLayerFormat;
import com.kennedysmithjava.dynamicdungeons.util.LootTable;
import com.kennedysmithjava.dynamicdungeons.util.Util;
import com.kennedysmithjava.dynamicdungeons.variables.ExpressionWrapper;

public class TestDungeon extends DungeonFormat {

    public TestDungeon() {
        setDungeonName("testDungeon");


        //Allow custom variables

        int maxPaths = 10;
        int maxPathLength = 20;

        String straightString = "1";
        String cornerString = "(path_past_straight > 2) & (random_decimal < 0.5)";
        String ascentString = "1";
        String branchString = "(path_past_straight > 3) & (random_decimal < 0.75)";
        String descentString = "0";

        ExpressionWrapper straightPredicate = new ExpressionWrapper(straightString);
        ExpressionWrapper cornerPredicate = new ExpressionWrapper(cornerString);
        ExpressionWrapper ascentPredicate = new ExpressionWrapper(ascentString);
        ExpressionWrapper descentPredicate = new ExpressionWrapper(descentString);
        ExpressionWrapper branchPredicate = new ExpressionWrapper(branchString);

        setLayerFormats(Util.map(
                0, new DungeonLayerFormat(
                        "TestLayer",
                        new LootTable(),
                        10,
                        maxPathLength,
                        straightPredicate,
                        cornerPredicate,
                        ascentPredicate,
                        descentPredicate,
                        branchPredicate
                )
        ));
    }


}
