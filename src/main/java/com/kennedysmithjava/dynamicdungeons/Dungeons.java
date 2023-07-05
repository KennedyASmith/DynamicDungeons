package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.cmd.CmdDungeon;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;
import com.kennedysmithjava.dynamicdungeons.cmd.CommandManager;

public final class Dungeons extends JavaPlugin {
    CommandManager commandManager;

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
        super.onLoad();
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();
        commandManager = new CommandManager(this);

        // Register commands
        commandManager.registerCommand(new CmdDungeon());
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
