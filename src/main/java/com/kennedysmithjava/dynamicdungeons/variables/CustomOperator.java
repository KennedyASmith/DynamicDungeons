package com.kennedysmithjava.dynamicdungeons.variables;

import net.objecthunter.exp4j.operator.Operator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public enum CustomOperator {

    AND(new Operator("&", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
        @Override
        public double apply(double... args) {
            return (args[0] != 0 && args[1] != 0) ? 1 : 0;
        }
    }),
    OR(new Operator("|", 2, true, Operator.PRECEDENCE_ADDITION - 2) {
        @Override
        public double apply(double... args) {
            // Perform the logical OR operation
            return (args[0] != 0 || args[1] != 0) ? 1 : 0;
        }
    }),
    LESS_THAN(new Operator("<", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
        @Override
        public double apply(double... args) {
            // Perform the less than operation
            return args[0] < args[1] ? 1 : 0;
        }
    }),
    GREATER_THAN(new Operator(">", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
        @Override
        public double apply(double... args) {
            // Perform the greater than operation
            return args[0] > args[1] ? 1 : 0;
        }
    })
    ;

    private final Operator operator;
    CustomOperator(Operator operator){
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public static List<Operator> getOperators(){
        List<Operator> operators = new ArrayList<>();
        for (CustomOperator value : values()) {
            operators.add(value.getOperator());
        }
        return operators;
    }

}
