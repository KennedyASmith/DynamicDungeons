package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.util.ChunkCoordinate;

public class PathContext {



    //functions like shouldAscent() will take a DungeonContext object

    //Total


    int pathLength = 0;
    ChunkCoordinate currentCoordinte;
    LayerContext layerContext;

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
        this.currentCoordinte = new ChunkCoordinate(currentChunkX, currentChunkY, currentChunkZ);
        this.layerContext = layerContext;
    }

    public PathContext(LayerContext layerContext, int currentChunkX, int currentChunkY, int currentChunkZ) {
        this.pathLength = 0;
        this.currentCoordinte = new ChunkCoordinate(currentChunkX, currentChunkY, currentChunkZ);
        this.layerContext = layerContext;
    }

    public int getPathLength() {
        return pathLength;
    }

    public int getX(){
        return currentCoordinte.getX();
    }
    public int getY(){
        return currentCoordinte.getY();
    }
    public int getZ(){
        return currentCoordinte.getZ();
    }

    public void incrementNodes(){
        this.pathLength += 1;
    }

    public void setCurrentCoordinate(ChunkCoordinate coordinate){
        this.currentCoordinte = coordinate;
    }

    public LayerContext getLayerContext() {
        return layerContext;
    }
}
