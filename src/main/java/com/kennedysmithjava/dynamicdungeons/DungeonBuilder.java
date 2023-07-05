package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.nodes.*;
import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.Color;
import com.kennedysmithjava.dynamicdungeons.util.Direction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

    //40 squares across, exactly
    //
    public void generate(int startLayer, int chunkX, int chunkY, int chunkZ){
        DungeonLayerFormat startLayerFormat = layerFormats.get(startLayer);
        LayerContext layerContext = new LayerContext(startLayer);
        PathContext pathContext = new PathContext(layerContext, chunkX, chunkY, chunkZ);
        Node start = startLayerFormat.genStart(Direction.NORTH, layerContext, pathContext);
        List<Node> layer = continuePath(Direction.NORTH, startLayerFormat, layerContext, pathContext);
        layer.add(start);
        this.addNodes(layer, startLayer);
    }

    public void preview(Player player){
        Map<ChunkCoordinate, Node> nodes = layerNodes.get(0);
        int startX = -15;
        int startZ = -15;
        int endX = 16;
        int endZ = 16;

        for (int x = startX; x < endX; x++) {
            StringBuilder row = new StringBuilder();
            for (int z = startZ; z < endZ; z++) {
                ChunkCoordinate coordinate = new ChunkCoordinate(x, 0, z);
                Node node = nodes.get(coordinate);
                if(node != null){
                    if(node instanceof NodeStart){
                        row.append("&a⬛");
                    }else if(node instanceof NodeEnd){
                        row.append("&c⬛");
                    }else{
                        row.append("&e⬛");
                    }
                }else{
                    row.append("&7⬛");
                }
            }
            player.sendMessage(Color.get(row.toString()));
        }
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
        }else if(canGenLeft(facing, pathContext) && layer.shouldGenLeft(pathContext)){
            node = layer.genLeft(facing, layerContext, pathContext);
            nodes.addAll(continuePath(facing.getLeft(), layer, layerContext, pathContext));
        }else if(canGenRight(facing, pathContext) && layer.shouldGenRight(pathContext)){
            node = layer.genRight(facing, layerContext, pathContext);
            nodes.addAll(continuePath(facing.getRight(), layer, layerContext, pathContext));
        }else if(canGenStraight(facing, pathContext) && layer.shouldGenStraight(pathContext)){
            node = layer.genStraight(facing, layerContext, pathContext);
            nodes.addAll(continuePath(facing, layer, layerContext, pathContext));
        }else if(canGenBranch(facing, pathContext) && layer.shouldGenBranch(pathContext)){
            node = layer.genBranch(facing, layerContext, pathContext);
            for (Direction branchFacing : Direction.allExcept(facing.invert())) {
                nodes.addAll(continuePath(branchFacing, layer, layerContext, new PathContext(layerContext, pathContext.currentCoordinte)));
            }
        } else {
            node = layer.genEnd(facing, layerContext, pathContext);
        }

        nodes.add(node);
        return nodes;

        /**/

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
        return switch (facing) {
            case NORTH ->
                    isFree(context.getX(), context.getY(), context.getZ() - 1); // This 1 should scale depending on schem size
            case WEST -> isFree(context.getX() - 1, context.getY(), context.getZ());
            case EAST -> isFree(context.getX() + 1, context.getY(), context.getZ());
            default -> isFree(context.getX(), context.getY(), context.getZ() + 1);
        };
    }

    public boolean canGenLeft(Direction facing, PathContext context){
        return switch (facing) {
            case NORTH -> isFree(context.getX() - 1, context.getY(), context.getZ());
            case WEST -> isFree(context.getX(), context.getY(), context.getZ() + 1);
            case EAST -> isFree(context.getX(), context.getY(), context.getZ() - 1);
            default -> isFree(context.getX() + 1, context.getY(), context.getZ());
        };
    }

    public boolean canGenRight(Direction facing, PathContext context){
        return switch (facing) {
            case NORTH -> isFree(context.getX() + 1, context.getY(), context.getZ());
            case WEST -> isFree(context.getX(), context.getY(), context.getZ() - 1);
            case EAST -> isFree(context.getX(), context.getY(), context.getZ() + 1);
            default -> isFree(context.getX() - 1, context.getY(), context.getZ());
        };
    }

    public boolean canGenBranch(Direction facing, PathContext context){
        return canGenLeft(facing, context) && canGenRight(facing, context) && canGenStraight(facing, context);
    }

    public void addNodes(List<Node> layer, int layerNumber){
        Map<ChunkCoordinate, Node> occupiedLayerLocations = layerNodes.getOrDefault(layerNumber, new HashMap<>());
        for (Node node : layer) {
            ChunkCoordinate chunkCoordinate = node.getCoordinate();
            occupiedLayerLocations.put(chunkCoordinate, node);
        }
        layerNodes.put(layerNumber, occupiedLayerLocations);
    }

    public boolean isFree(int chunkX, int chunkY, int chunkZ){
        ChunkCoordinate coordinate = new ChunkCoordinate(chunkX, chunkY, chunkZ);
        for (Map<ChunkCoordinate, Node> map : layerNodes.values()) {
            if(map.containsKey(coordinate)){
                return false;
            }
        }
        return true;
    }

}
