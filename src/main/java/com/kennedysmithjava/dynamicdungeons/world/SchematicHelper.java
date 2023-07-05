package com.kennedysmithjava.dynamicdungeons.world;

import com.fastasyncworldedit.core.FaweAPI;
import com.fastasyncworldedit.core.util.TaskManager;
import com.fastasyncworldedit.core.util.task.RunnableVal;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class SchematicHelper {
    private final WorldEdit worldEdit;
    private final World world;

    public SchematicHelper(World world) {
        this.worldEdit = WorldEdit.getInstance();
        this.world = world;
    }

    public void pasteSchematic(Location location, Clipboard clipboard, Runnable whenDone) {
        try (EditSession es = worldEdit.newEditSessionBuilder()
                .world(world)
                .build()) {
            Runnable runnable = () -> new ClipboardHolder(clipboard)
                    .createPaste(es)
                    .to(BlockVector3.at(location.getBlockX(), location.getY(), location.getBlockZ()))
                    .build();
            TaskManager taskManager = FaweAPI.getTaskManager();
            taskManager.async(runnable);
            taskManager.syncWhenFree(new RunnableVal<Object>(whenDone) {
                @Override
                public void run(Object value) {
                    whenDone.run();
                }
            });
        }
    }

    public static Clipboard buildClipboard(String schematicPath) throws IOException {
        File schematicFile = new File(schematicPath);
        if (!schematicFile.exists()) throw new IOException("File does not exist: " + schematicPath);
        return FaweAPI.load(schematicFile);
    }
}
