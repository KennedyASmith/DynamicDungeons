package com.kennedysmithjava.dynamicdungeons;

public class LayerContext {

    final int layer;
    int totalNodes;
    int paths;

    public LayerContext(int layer, int totalNodes, int paths) {
        this.layer = layer;
        this.totalNodes = totalNodes;
        this.paths = paths;
    }

    public LayerContext(int layer) {
        this.layer = layer;
        this.totalNodes = 0;
        this.paths = 1;
    }

    public int getLayer() {
        return layer;
    }

    public int getTotalNodes() {
        return totalNodes;
    }

    public int getPaths() {
        return paths;
    }

    public void incrementNodes(){
        this.totalNodes += 1;
    }

    public void incrementPaths(){
        this.paths += 1;
    }
}
