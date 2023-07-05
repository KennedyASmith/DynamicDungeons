package com.kennedysmithjava.dynamicdungeons;

import java.util.Map;

public class DungeonFormat {


    /*
        Config:

        Use Chunk Size = base size

        Generate for every DungeonFormat:
            folder-name: "runes"
            layers-above-spawn: 0 //-1 for unlimited
            layers-below-spawn: 20 //-1 for unlimited
            invert-schem-usage: true //Uses a given file path for all levels above rather than below
            use-global-loot-table: true //Use below if so
                global-loot-table-name: "rare-loot-table"
                global-loot-rarity: "(layer / rarity)" // Available variables

        File Structure:
            /plugin/schematics/layer_1/ //Above
            /plugin/schematics/layer_0/ //Spawn level
            /plugin/schematics/layer_-1/ //Uses this path for all below -1
            /plugin/schematics/layer_-10/ //All levels beneath use -10

        For every layer:
            /layer_1/config.yml
            /layer_1/paths/Corners
            /layer_1/paths/Corners/Left
            /layer_1/paths/Corners/Right
            /layer_1/paths/Ascend/Stair
            /layer_1/paths/Ascend/Ladder
            /layer_1/paths/Descend/Stair
            /layer_1/paths/Descend/Ladder
            /layer_1/paths/Straight/

        Iterate through all layer paths to check proper file names. (Starts with layer_, ends with int)
     */

    String dungeonName;

    Map<Integer, DungeonLayerFormat> layerFormats;

    public DungeonFormat() {
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    public void setLayerFormats(Map<Integer, DungeonLayerFormat> layerFormats) {
        this.layerFormats = layerFormats;
    }

    public Map<Integer, DungeonLayerFormat> getLayerFormats() {
        return layerFormats;
    }
}
