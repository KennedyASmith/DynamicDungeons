package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.nodes.*;
import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DungeonBuilder {
    private final Map<Integer, DungeonLayerFormat> layerFormats;
    private final Map<Integer, Map<ChunkCoordinate, Node>> layerNodes = new HashMap<>();

    public DungeonBuilder(DungeonFormat dungeonFormat){
         this.layerFormats = dungeonFormat.getLayerFormats();
    }

    public void generate(int startLayer, int chunkX, int chunkY, int chunkZ){
        DungeonLayerFormat startLayerFormat = layerFormats.get(startLayer);
        LayerContext layerContext = new LayerContext(startLayer);
        PathContext pathContext = new PathContext(layerContext, chunkX, chunkY, chunkZ);
        Node start = startLayerFormat.genStart(Direction.NORTH, layerContext, pathContext);
        List<Node> layer = continuePath(Direction.NORTH, startLayerFormat, layerContext, pathContext);
        layer.add(start);
        this.addNodes(layer, startLayer);
    }

    /*
        [=][=][=]
        [0][0][0]
        [0](\)[0]
     */

    public List<Node> continuePath(Direction facing, DungeonLayerFormat layer, LayerContext layerContext, PathContext pathContext){
        List<Node> nodes = new ArrayList<>();
        Node node;
        if(pathContext.getPathLength() >= layer.getMaxPathLength()){
            node = layer.genEnd(facing, layerContext, pathContext);
        }else if(canGenStraight(facing, pathContext) && layer.shouldGenStraight(pathContext)){
            node = layer.genStraight(facing, layerContext, pathContext);
            nodes.addAll(continuePath(facing, layer, layerContext, pathContext));
        }else if(canGenLeft(facing, pathContext) && layer.shouldGenLeft(pathContext)){
            node = layer.genLeft(facing, layerContext, pathContext);
            nodes.addAll(continuePath(facing.getLeft(), layer, layerContext, pathContext));
        }else if(canGenRight(facing, pathContext) && layer.shouldGenRight(pathContext)){
            node = layer.genRight(facing, layerContext, pathContext);
            nodes.addAll(continuePath(facing.getRight(), layer, layerContext, pathContext));
        } else {
            node = layer.genEnd(facing, layerContext, pathContext);
        }

        nodes.add(node);
        return nodes;

        /*if(canGenBranch(facing, pathContext) && layer.shouldGenBranch(pathContext)){
            NodeBranch node = layer.genBranch(facing, layerContext, pathContext);
            List<Direction> branches = node.getBranches();
            for (Direction branchFacing : branches) {
                continuePath(node, branchFacing, layer, layerContext, pathContext);
            }
            return;
        }*/

        /*if(canGenAscend(facing, pathContext) && layer.shouldGenAscend(pathContext)){
            NodeAscent node = layer.genAscend(layerContext, pathContext);
            int nextLayerNumber = layerContext.getCurrentLayer() + 1;
            DungeonLayerFormat nextLayer = this.layerFormats.get(nextLayerNumber);
            LayerContext newLayerContext = this.getLayerContext(nextLayerNumber);
            PathContext newPathContext = nextLayer.genNewPathContext(newLayerContext);
            continuePath(node, facing, nextLayer, newLayerContext, newPathContext);
            return;
        }

        if(canGenDescend(facing, pathContext) && layer.shouldGenDescend(pathContext)){
            NodeDescent node = layer.genDescend(layerContext, pathContext);
            int nextLayerNumber = layerContext.getCurrentLayer() - 1;
            DungeonLayerFormat nextLayer = this.layerFormats.get(nextLayerNumber);
            LayerContext newLayerContext = this.getLayerContext(nextLayerNumber);
            PathContext newPathContext = nextLayer.genNewPathContext(newLayerContext);
            continuePath(node, facing, nextLayer, newLayerContext, newPathContext);
            return;
        }*/
    }

    public boolean canGenStraight(Direction facing, PathContext context){
        switch(facing){
            case NORTH:
                return isFree(context.getX(), context.getY(), context.getZ() - 1); // This 1 should scale depending on schem size
            case WEST:
                return isFree(context.getX() - 1, context.getY(), context.getZ());
            case EAST:
                return isFree(context.getX() + 1, context.getY(), context.getZ());
            default:
                return isFree(context.getX(), context.getY(), context.getZ() + 1);
        }
    }

    public boolean canGenLeft(Direction facing, PathContext context){
        switch(facing){
            case NORTH:
                return isFree(context.getX() - 1, context.getY(), context.getZ());
            case WEST:
                return isFree(context.getX(), context.getY(), context.getZ() + 1);
            case EAST:
                return isFree(context.getX(), context.getY(), context.getZ() - 1);
            default:
                return isFree(context.getX() + 1, context.getY(), context.getZ());
        }
    }

    public boolean canGenRight(Direction facing, PathContext context){
        switch(facing){
            case NORTH:
                return isFree(context.getX() + 1, context.getY(), context.getZ());
            case WEST:
                return isFree(context.getX(), context.getY(), context.getZ() - 1);
            case EAST:
                return isFree(context.getX(), context.getY(), context.getZ() + 1);
            default:
                return isFree(context.getX() - 1, context.getY(), context.getZ());
        }
    }

    public void addNodes(List<Node> layer, int layerNumber){
        Map<ChunkCoordinate, Node> occupiedLayerLocations = layerNodes.getOrDefault(layerNumber, new HashMap<>());
        for (Node node : layer) {
            ChunkCoordinate chunkCoordinate = node.getCoordinate();
            occupiedLayerLocations.put(chunkCoordinate, node);
        }
        layerNodes.put(layerNumber, occupiedLayerLocations);
    }

    public void startRoom(int chunkX, int y, int chunkZ, Direction direction, DungeonLayerFormat layer){

    }

    public boolean isFree(int chunkX, int y, int chunkZ){
        //Check if
        return false;
    }

    public LayerContext getLayerContext(int layerNumber){
        //If one exists already, return. If not, create a new one
        return null;
    }
}
