package com.kennedysmithjava.dynamicdungeons.variables;

import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public enum CustomFunctions {

    AND(new Function("range", 2) {
        @Override
        public double apply(double... args) {
            double min = args[0];
            double max = args[1];

            // Generate a random number within the range
            return ThreadLocalRandom.current().nextDouble(min, max);
        }
    });

    private final Function function;
    CustomFunctions(Function function){
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }

    public static List<Function> getFunctions(){
        List<Function> functions = new ArrayList<>();
        for (CustomFunctions value : values()) {
            functions.add(value.getFunction());
        }
        return functions;
    }

}
