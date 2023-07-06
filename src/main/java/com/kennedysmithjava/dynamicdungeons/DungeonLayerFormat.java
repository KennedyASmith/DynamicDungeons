package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.nodes.*;
import com.kennedysmithjava.dynamicdungeons.nodes.format.*;
import com.kennedysmithjava.dynamicdungeons.util.*;
import com.kennedysmithjava.dynamicdungeons.variables.ExpressionWrapper;
import net.objecthunter.exp4j.Expression;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.bukkit.Bukkit;

import java.util.List;


/**
 * To be replaced with CustomLayerFormat that extends DungeonLayerFormat
 */
public class DungeonLayerFormat {


    /*
        Config:

        Handle what happens when a corridor is entered

        Check all variables in all functions/predicates to see if zero division is possible

        Generate for every DungeonFormat:
            folder-name: "runes"
            layers-above-spawn: 0 //-1 for unlimited
            layers-below-spawn: 20 //-1 for unlimited
            invert-schem-usage: true //Uses a given file path for all levels above rather than below
            use-global-loot-table: true //Use below if so
                global-loot-table-name: "rare-loot-table"
                global-loot-luck: "1 - abs(1/(%layer% + 1))" // Loot gets better as you go further away from layer 0
                global-loot-gen-predicate: "(-6 < %layer% < 1) & (%random_decimal% < 0.25)" //Loot can generate on layers -5 to 0 with a chance of 0.25

        File Structure:
            /plugin/schematics/layer_1/ //Above
            /plugin/schematics/layer_0/ //Spawn level
            /plugin/schematics/layer_-1/ //Uses this path for all below -1
            /plugin/schematics/layer_-10/ //All levels beneath use -10

        For every layer:
            /layer_1/config.yml
            /layer_1/nodes/Corners
            /layer_1/nodes/Corners/Left
            /layer_1/nodes/Corners/Right
            /layer_1/nodes/Ascend/Stair
            /layer_1/nodes/Ascend/Ladder
            /layer_1/nodes/Descend/Stair
            /layer_1/nodes/Descend/Ladder
            /layer_1/nodes/Straight/

        /Check if a predicate/function is contextual or static on load
        /Some prefixes could evaluate to statements such as %false% or %true% to 1 = 2 or 1 = 1
        Layer config:
            loot-table-name: "rare-loot-table"
            loot-luck: "(%layer% / %loot_rarity%)" //0 for no change, Available variables, use placeholder api integration too
            loot-gen-predicate: "(-6 < %layer% < 1) & (%random_decimal% < 0.25)" //Loot can generate on layers -5 to 0 with a chance of 0.25

            //Regardless of the value entered for end-predicate, an end schematic will always be pasted if
            //current-nodes max-nodes is exceeded or nothing else can be pasted.

            straight-predicate: "%true%" //Always generate a straight path
            end-predicate: "(%dungeon_total_nodes% > 10) & (%path_total_straight% > 5) (%random_decimal% < 0.5)"
            branch-predicate: "%true%" //Always generate a straight path
            branch-predicate: "(%dungeon_total_paths% < 5) & (%path_total_straight% > 5) (%random_decimal% < 0.5)"
            descent-predicate: "(%dungeon_total_straight% > 5) & (%random_decimal% < 0.5) & (%dungeon_total_descent% < 3)"
            ascent-predicate: "%false%" # Never allow re-ascent

        Iterate through all layer paths to check proper file names. (Starts with layer_, ends with int)
     */

    String layerName;
    LootTable lootTable;
    int maxPaths;
    int maxPathLength;
    EnumeratedDistribution<NodeFormatStraight> straightNodeFormats;
    EnumeratedDistribution<NodeFormatRightCorner> rightNodeFormats;
    EnumeratedDistribution<NodeFormatLeftCorner> leftNodeFormats;
    EnumeratedDistribution<NodeFormatAscent> ascentNodeFormats;
    EnumeratedDistribution<NodeFormatDescent> descentNodeFormats;
    ExpressionWrapper straightPredicate;
    ExpressionWrapper cornerPredicate;
    ExpressionWrapper ascentPredicate;
    ExpressionWrapper descentPredicate;
    ExpressionWrapper branchPredicate;

    public DungeonLayerFormat(String layerName, LootTable lootTable, int maxPaths, int maxPathLength, ExpressionWrapper straightPredicate, ExpressionWrapper cornerPredicate, ExpressionWrapper ascentPredicate, ExpressionWrapper descentPredicate, ExpressionWrapper branchPredicate) {
        this.layerName = layerName;
        this.lootTable = lootTable;
        this.maxPaths = maxPaths;
        this.maxPathLength = maxPathLength;
        this.straightPredicate = straightPredicate;
        this.cornerPredicate = cornerPredicate;
        this.ascentPredicate = ascentPredicate;
        this.descentPredicate = descentPredicate;
        this.branchPredicate = branchPredicate;
    }

    public boolean shouldGenStraight(PathContext context){
        Expression expression = resolveContextPlaceholders(context, straightPredicate);
        double eval = expression.evaluate();
        Bukkit.broadcastMessage("Straight eval: " + eval);
        return eval == 1D;
    }

    public boolean shouldGenLeft(PathContext context){
        Expression expression = resolveContextPlaceholders(context, cornerPredicate);
        double eval = expression.evaluate();
        Bukkit.broadcastMessage("Left eval: " + eval);
        return eval == 1D;
    }

    public boolean shouldGenRight(PathContext context){
        Expression expression = resolveContextPlaceholders(context, cornerPredicate);
        double eval = expression.evaluate();
        Bukkit.broadcastMessage("Right eval: " + eval);
        return eval == 1D;
    }

    public boolean shouldGenAscent(PathContext context){
        Expression expression = resolveContextPlaceholders(context, ascentPredicate);
        return expression.evaluate() == 1D;
    }

    public boolean shouldGenDescent(PathContext context){
        Expression expression = resolveContextPlaceholders(context, descentPredicate);
        return expression.evaluate() == 1D;
    }

    public boolean shouldGenBranch(PathContext context){
        Expression expression = resolveContextPlaceholders(context, branchPredicate);
        double eval = expression.evaluate();
        return eval == 1D;
    }

    public Node genStart(Direction direction, LayerContext layerContext, PathContext pathContext){
        NodeStart node = new NodeStart(pathContext.getCurrentCoordinate(), direction);
        node.setColor(pathContext.getColor());
        pathContext.incrementLastNode(node.getType());
        layerContext.incrementNodes();
        pathContext.incrementNodes();
        ChunkCoordinate newChunkCoordinate = direction.getFrontCoordinate(pathContext.getCurrentCoordinate());
        pathContext.setCurrentCoordinate(newChunkCoordinate);
        this.paste(node, newChunkCoordinate);
        return node;
    }

    public Node genStraight(Direction direction, LayerContext layerContext, PathContext pathContext){
        NodeStraight node = new NodeStraight(pathContext.getCurrentCoordinate(), direction);
        node.setColor(pathContext.getColor());
        pathContext.incrementLastNode(node.getType());
        layerContext.incrementNodes();
        pathContext.incrementNodes();
        ChunkCoordinate newChunkCoordinate = direction.getFrontCoordinate(pathContext.getCurrentCoordinate());
        pathContext.setCurrentCoordinate(newChunkCoordinate);
        this.paste(node, newChunkCoordinate);
        return node;
    }

    public Node genEnd(Direction direction, LayerContext layerContext, PathContext pathContext){
        NodeEnd node = new NodeEnd(pathContext.getCurrentCoordinate(), direction);
        node.setColor(pathContext.getColor());
        pathContext.incrementLastNode(node.getType());
        layerContext.incrementNodes();
        pathContext.incrementNodes();
        ChunkCoordinate newChunkCoordinate = direction.getFrontCoordinate(pathContext.getCurrentCoordinate());
        pathContext.setCurrentCoordinate(newChunkCoordinate);
        this.paste(node, newChunkCoordinate);
        return node;
    }


    public Node genLeft(Direction direction, LayerContext layerContext, PathContext pathContext){
        NodeLeftCorner node = new NodeLeftCorner(pathContext.getCurrentCoordinate(), direction);
        node.setColor(pathContext.getColor());
        pathContext.incrementLastNode(node.getType());
        layerContext.incrementNodes();
        pathContext.incrementNodes();
        ChunkCoordinate newChunkCoordinate = direction.getLeftCoordinate(pathContext.getCurrentCoordinate());
        pathContext.setCurrentCoordinate(newChunkCoordinate);
        this.paste(node, newChunkCoordinate);
        return node;
    }

    public Node genRight(Direction direction, LayerContext layerContext, PathContext pathContext){
        NodeRightCorner node = new NodeRightCorner(pathContext.getCurrentCoordinate(), direction);
        node.setColor(pathContext.getColor());
        pathContext.incrementLastNode(node.getType());
        layerContext.incrementNodes();
        pathContext.incrementNodes();
        ChunkCoordinate newChunkCoordinate = direction.getRightCoordinate(pathContext.getCurrentCoordinate());
        pathContext.setCurrentCoordinate(newChunkCoordinate);
        this.paste(node, newChunkCoordinate);
        return node;
    }

    public Node genBranch(Direction direction, TypeBranch branchType, LayerContext layerContext, PathContext pathContext){
        NodeBranch node = new NodeBranch(pathContext.getCurrentCoordinate(), direction, branchType);
        node.setColor(pathContext.getColor());
        pathContext.incrementLastNode(node.getType());
        layerContext.incrementNodes();
        pathContext.incrementNodes();
        /* This path ends */
        return node;
    }

    public Expression resolveContextPlaceholders(PathContext context, ExpressionWrapper expressionWrapper){
        Expression expression = expressionWrapper.getExpression();
        expressionWrapper.getVariables().forEach((variable, variableEvaluator) -> {
            double val = variableEvaluator.apply(context);
            expression.setVariable(variable, val);
        });
        return expression;
    }

    public void paste(Node node, ChunkCoordinate chunkCoordinate){

    }

    public int getMaxPathLength() {
        return maxPathLength;
    }

}
