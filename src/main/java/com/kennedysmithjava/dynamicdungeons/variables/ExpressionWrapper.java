package com.kennedysmithjava.dynamicdungeons.variables;

import com.kennedysmithjava.dynamicdungeons.PathContext;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Map;
import java.util.function.Function;

public class ExpressionWrapper {

    private final Expression expression;
    private final Map<String, Function<PathContext, Double>> variables;

    public ExpressionWrapper(String expression) {
        this.variables = ContextVariable.extract(expression);
        this.expression = new ExpressionBuilder(expression).variables(variables.keySet()).build();
    }

    public Map<String, Function<PathContext, Double>> getVariables() {
        return variables;
    }

    public Expression getExpression() {
        return expression;
    }
}
