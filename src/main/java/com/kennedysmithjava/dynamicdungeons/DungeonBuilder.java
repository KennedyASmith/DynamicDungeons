package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.nodes.*;
import com.kennedysmithjava.dynamicdungeons.util.*;
import org.bukkit.entity.Player;

import java.util.*;

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
        addNode(startLayer, start);
        continuePath(Direction.NORTH, startLayer, startLayerFormat, layerContext, pathContext);
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
                    row.append(node.getColoredSymbol());
                }else{
                    row.append("&8⧇");
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

    private final static LoopIterator<String> colorIterator = new LoopIterator<>(Util.list("&e", "&b", "&a", "&9", "&7", "&6", "&5", "&3", "&1"));


    public void continuePath(Direction facing, int layer, DungeonLayerFormat lf, LayerContext layerContext, PathContext pathContext){
        Node node;
        if(pathContext.getPathLength() >= lf.getMaxPathLength()){
            node = lf.genEnd(facing, layerContext, pathContext);
            addNode(layer, node);
        }else if(canGenLeft(facing, pathContext) && lf.shouldGenLeft(pathContext)){
            node = lf.genLeft(facing, layerContext, pathContext);
            addNode(layer, node);
            continuePath(facing.getLeft(), layer, lf, layerContext, pathContext);

        }else if(canGenRight(facing, pathContext) && lf.shouldGenRight(pathContext)){
            node = lf.genRight(facing, layerContext, pathContext);
            addNode(layer, node);
            continuePath(facing.getRight(), layer, lf, layerContext, pathContext);

        }else if(canGenBranch(facing, pathContext) && lf.shouldGenBranch(pathContext)){
            List<TypeBranch> availableBranches = getBranchAvailability(facing, pathContext);
            TypeBranch chosenBranch = Util.pickRandom(availableBranches);
            ChunkCoordinate currentCoordinate = pathContext.getCurrentCoordinate();
            node = lf.genBranch(facing, chosenBranch, layerContext, pathContext);
            addNode(layer, node);
            chosenBranch.getDirections(facing, currentCoordinate).forEach((direction, coordinate) -> {
                PathContext newContext = new PathContext(layerContext, coordinate);
                newContext.setColor(colorIterator.next());
                continuePath(direction, layer, lf, layerContext, newContext);
            });

        } else if(canGenStraight(facing, pathContext) && lf.shouldGenStraight(pathContext)){
            node = lf.genStraight(facing, layerContext, pathContext);
            addNode(layer, node);
            continuePath(facing, layer, lf, layerContext, pathContext);

        }else {
            node = lf.genEnd(facing, layerContext, pathContext);
            addNode(layer, node);
        }

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
            case EAST -> isFree( context.getX() + 1, context.getY(), context.getZ());
            default -> isFree( context.getX(), context.getY(), context.getZ() + 1);
        };
    }

    public boolean canGenLeft( Direction facing, PathContext context){
        return switch (facing) {
            case NORTH -> isFree(context.getX() - 1, context.getY(), context.getZ());
            case WEST -> isFree( context.getX(), context.getY(), context.getZ() + 1);
            case EAST -> isFree( context.getX(), context.getY(), context.getZ() - 1);
            default -> isFree(context.getX() + 1, context.getY(), context.getZ());
        };
    }

    public boolean canGenRight(Direction facing, PathContext context){
        return switch (facing) {
            case NORTH -> isFree( context.getX() + 1, context.getY(), context.getZ());
            case WEST -> isFree(context.getX(), context.getY(), context.getZ() - 1);
            case EAST -> isFree(context.getX(), context.getY(), context.getZ() + 1);
            default -> isFree(context.getX() - 1, context.getY(), context.getZ());
        };
    }


    /*
    Cross
    [0][=][0]
    [=][=][=]
    [0][x][0]

    Left
    [0][=][0]
    [=][=][0]
    [0][x][0]

    Right
    [0][=][0]
    [0][=][=]
    [0][x][0]

    Tee
    [0][0][0]
    [=][=][=]
    [0][x][0]

 */

    public boolean canGenBranch(Direction facing, PathContext context){
        boolean leftFree = canGenLeft(facing, context);
        boolean rightFree = canGenRight(facing, context);
        boolean straightFree = canGenRight(facing, context);
        if(straightFree && leftFree && rightFree){
            return true;
        }
        if(straightFree && leftFree){
            return true;
        }
        if(straightFree && rightFree){
            return true;
        }
        return rightFree && leftFree;
    }

    public List<TypeBranch> getBranchAvailability(Direction facing, PathContext context){
        boolean leftFree = canGenLeft(facing, context);
        boolean rightFree = canGenRight(facing, context);
        boolean straightFree = canGenRight(facing, context);
        List<TypeBranch> branchTypes = new ArrayList<>();
        if(straightFree && leftFree && rightFree){
            branchTypes.add(TypeBranch.CROSS);
        }
        if(straightFree && leftFree && !rightFree){
            branchTypes.add(TypeBranch.LEFT);
        }
        if(straightFree && !leftFree && rightFree){
            branchTypes.add(TypeBranch.RIGHT);
        }
        if(!straightFree && rightFree && leftFree){
            branchTypes.add(TypeBranch.TEE);
        }

        return branchTypes;
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

    public void addNode(int layer, Node node){
        Map<ChunkCoordinate, Node> nodes = layerNodes.getOrDefault(layer, new HashMap<>());
        nodes.put(node.getCoordinate(), node);
        layerNodes.put(layer, nodes);
    }

}
