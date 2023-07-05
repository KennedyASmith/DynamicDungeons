package com.kennedysmithjava.dynamicdungeons.variables;

import com.kennedysmithjava.dynamicdungeons.PathContext;
import com.kennedysmithjava.dynamicdungeons.nodes.TypeNode;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public enum Variable {

    ALWAYS("always", pathContext -> (double) 1), //TODO: Non-contextual
    NEVER("never", pathContext -> (double) 0),
    LAYER("layer", pathContext -> (double) pathContext.getLayerContext().getLayer()),
    RANDOM_DECIMAL("random_decimal", pathContext -> ThreadLocalRandom.current().nextDouble()),
    LAYER_TOTAL_DESCENT("layer_total_descent", pathContext -> 0D),
    PATH_PAST_STRAIGHT("path_past_straight", pathContext -> (double) pathContext.countLastNode(TypeNode.STRAIGHT)),
    PATH_PAST_CORNER("path_past_corner", pathContext -> (double) pathContext.countLastNode(TypeNode.LEFT_CORNER) + pathContext.countLastNode(TypeNode.RIGHT_CORNER)),
    PATH_PAST_BRANCH("path_past_branch", pathContext -> (double) pathContext.countLastNode(TypeNode.BRANCH)),
    ;

    final String placeholder;
    final Function<PathContext, Double> replacer;

    Variable(String placeholder, Function<PathContext, Double> replacer) {
        this.placeholder = placeholder;
        this.replacer = replacer;
    }


    public static Map<String, Function<PathContext, Double>> extract(String expression){
        Map<String, Function<PathContext, Double>> variables = new HashMap<>();
        for (Variable value : values()) {
            int index = expression.indexOf(value.getPlaceholder());
            if(index != -1){
                variables.put(value.getPlaceholder(), value.getReplacer());
            }
        }
        return variables;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Function<PathContext, Double> getReplacer() {
        return replacer;
    }
}
