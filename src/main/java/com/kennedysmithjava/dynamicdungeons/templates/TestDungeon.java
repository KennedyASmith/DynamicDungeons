package com.kennedysmithjava.dynamicdungeons.templates;

import com.kennedysmithjava.dynamicdungeons.DungeonFormat;
import com.kennedysmithjava.dynamicdungeons.DungeonLayerFormat;
import com.kennedysmithjava.dynamicdungeons.util.LootTable;
import com.kennedysmithjava.dynamicdungeons.util.Util;
import com.kennedysmithjava.dynamicdungeons.variables.ContextVariable;
import com.kennedysmithjava.dynamicdungeons.variables.ExpressionWrapper;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class TestDungeon extends DungeonFormat {

    public TestDungeon() {
        setDungeonName("testDungeon");

        String straightString = "%true%";
        String cornerString = "(%dungeon_total_nodes% > 10) & (%path_straight_length% > 5) & (%random_decimal% < 0.5)";
        String ascentString = "(%path_straight_length% > 5) & (%random_decimal% < 0.5) & (%layer_total_descent% < 3)";
        String descentString = "%false%";

        ExpressionWrapper straightPredicate = new ExpressionWrapper(straightString);
        ExpressionWrapper cornerPredicate = new ExpressionWrapper(cornerString);
        ExpressionWrapper ascentPredicate = new ExpressionWrapper(ascentString);
        ExpressionWrapper descentPredicate = new ExpressionWrapper(descentString);

        setLayerFormats(Util.map(
                0, new DungeonLayerFormat(
                        "TestLayer",
                        new LootTable(),
                        10,
                        10,
                        straightPredicate,
                        cornerPredicate,
                        ascentPredicate,
                        descentPredicate
                )
        ));
    }


}
