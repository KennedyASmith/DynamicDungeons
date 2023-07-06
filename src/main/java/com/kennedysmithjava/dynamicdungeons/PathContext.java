package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.nodes.TypeNode;
import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;
import com.kennedysmithjava.dynamicdungeons.util.NodeTypeRecord;

public class PathContext {



    //functions like shouldAscent() will take a DungeonContext object

    //Total

    //Count branches in a path
    //Current path depth and overall depth

    private int pathLength = 0;
    private ChunkCoordinate currentCoordinate;
    private LayerContext layerContext;
    private String color = "&7";

    private NodeTypeRecord nodeTypeRecord;

    /*

        int maxGlobalNodes = context.getMaxGlobalNodes();
        if(maxGlobalNodes != -1){
            if(globalNodes > maxGlobalNodes){
                dont generate more
            }
        }
     */

    public PathContext(LayerContext layerContext, int pathLength, int currentChunkX, int currentChunkY, int currentChunkZ) {
        this.pathLength = pathLength;
        this.currentCoordinate = new ChunkCoordinate(currentChunkX, currentChunkY, currentChunkZ);
        this.layerContext = layerContext;
    }

    public PathContext(LayerContext layerContext, int currentChunkX, int currentChunkY, int currentChunkZ) {
        this.pathLength = 0;
        this.currentCoordinate = new ChunkCoordinate(currentChunkX, currentChunkY, currentChunkZ);
        this.layerContext = layerContext;
    }

    public PathContext(LayerContext layerContext, ChunkCoordinate coordinate) {
        this.pathLength = 0;
        this.currentCoordinate = coordinate;
        this.layerContext = layerContext;
    }

    public int getPathLength() {
        return pathLength;
    }

    public int getX(){
        return currentCoordinate.getX();
    }
    public int getY(){
        return currentCoordinate.getY();
    }
    public int getZ(){
        return currentCoordinate.getZ();
    }

    public void incrementNodes(){
        this.pathLength += 1;
    }

    public void setCurrentCoordinate(ChunkCoordinate coordinate){
        this.currentCoordinate = coordinate;
    }

    public LayerContext getLayerContext() {
        return layerContext;
    }

    public void incrementLastNode(TypeNode type){
        if(nodeTypeRecord == null || nodeTypeRecord.getLastNodeType() != type){
            nodeTypeRecord = new NodeTypeRecord(type, 1);
        }else{
            nodeTypeRecord.incrementCount();
        }
    }

    public int countLastNode(TypeNode type){
        if(nodeTypeRecord == null) return 0;
        if(nodeTypeRecord.getLastNodeType() != type) return 0;
        return nodeTypeRecord.getCount();
    }

    public ChunkCoordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
