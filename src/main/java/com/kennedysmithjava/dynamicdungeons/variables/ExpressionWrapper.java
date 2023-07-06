package com.kennedysmithjava.dynamicdungeons.variables;

import com.kennedysmithjava.dynamicdungeons.PathContext;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.operator.Operator;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ExpressionWrapper {

    private final Expression expression;
    private final Map<String, Function<PathContext, Double>> variables;

    private final static List<Operator> operators = CustomOperator.getOperators();
    private final static List<net.objecthunter.exp4j.function.Function> functions = CustomFunctions.getFunctions();


    public ExpressionWrapper(String expression) {
        this.variables = Variable.extract(expression);
        this.expression = new ExpressionBuilder(expression).functions(functions).operator(operators).variables(variables.keySet()).build();
    }

    public Map<String, Function<PathContext, Double>> getVariables() {
        return variables;
    }

    public Expression getExpression() {
        return expression;
    }
}
