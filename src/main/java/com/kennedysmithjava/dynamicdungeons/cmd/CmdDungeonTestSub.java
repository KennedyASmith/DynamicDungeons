package com.kennedysmithjava.dynamicdungeons.cmd;

import com.kennedysmithjava.dynamicdungeons.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class CmdDungeonTestSub extends AbstractCommand {
    public CmdDungeonTestSub() {
        super("TestSubCommand", "Sub command test", "/dungeon this", "dungeon.admin.sub", Util.list("sub"));
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Bukkit.broadcastMessage("Args: " + Arrays.toString(args));
    }
}
