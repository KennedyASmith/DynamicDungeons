package com.kennedysmithjava.dynamicdungeons;

import com.kennedysmithjava.dynamicdungeons.cmd.CmdDungeon;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dungeons extends JavaPlugin {

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this).silentLogs(true));
        super.onLoad();
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();
        CommandAPI.registerCommand(CmdDungeon.class);
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}
