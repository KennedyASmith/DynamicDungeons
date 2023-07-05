package com.kennedysmithjava.dynamicdungeons.variables;

import com.kennedysmithjava.dynamicdungeons.PathContext;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public enum ContextVariable {

    TRUE("%true%", pathContext -> (double) 1), //TODO: Non-contextual
    FALSE("%false%", pathContext -> (double) 0),
    LAYER("%layer%", pathContext -> (double) pathContext.getLayerContext().getLayer()),
    RANDOM_DECIMAL("%random_decimal%", pathContext -> ThreadLocalRandom.current().nextDouble()),
    LAYER_TOTAL_DESCENT("%layer_total_descent%", pathContext -> 1D),
    PATH_PAST_STRAIGHT("%path_past_straight%", pathContext -> (double) pathContext.getLayerContext().getLayer()),
    PATH_PAST_CORNER("%path_past_corner%", pathContext -> (double) pathContext.getLayerContext().getLayer()),
    PATH_PAST_BRANCH("%path_past_branch%", pathContext -> 0D),
    ;

    String placeholder;
    Function<PathContext, Double> replacer;
    String straightString = "%true%";
    String cornerString = "(%path_past_corner% < 1) & (%path_past_straight% > 5) & (%random_decimal% < 0.5)";
    String ascentString = "(%path_straight_length% > 5) & (%random_decimal% < 0.5) & (%layer_total_descent% < 3)";
    String descentString = "%false%";

    ContextVariable(String placeholder, Function<PathContext, Double> replacer) {
        this.placeholder = placeholder;
        this.replacer = replacer;
    }


    public static Map<String, Function<PathContext, Double>> extract(String expression){
        Map<String, Function<PathContext, Double>> variables = new HashMap<>();
        for (ContextVariable value : values()) {
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
