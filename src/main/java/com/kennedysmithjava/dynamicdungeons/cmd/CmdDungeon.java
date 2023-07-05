package com.kennedysmithjava.dynamicdungeons.cmd;

import com.kennedysmithjava.dynamicdungeons.DungeonBuilder;
import com.kennedysmithjava.dynamicdungeons.templates.TestDungeon;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("dungeon")
public class CmdDungeon {

    @Default
    public static void dungeonDefault(CommandSender sender) {
        sender.sendMessage("--- Dungeon help ---");
        sender.sendMessage("/dungeon - Show this help");
        sender.sendMessage("/dungeon test - Test a dungeon");
    }

    @Subcommand("test")
    @Permission("dungeon.test")
    public static void dungeonTest(Player player) {
        DungeonBuilder dungeonBuilder = new DungeonBuilder(new TestDungeon());
        dungeonBuilder.generate(0, 0, 0, 0);
        dungeonBuilder.preview(player);
    }

}
