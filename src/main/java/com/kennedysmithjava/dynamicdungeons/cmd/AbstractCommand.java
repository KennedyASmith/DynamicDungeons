package com.kennedysmithjava.dynamicdungeons.cmd;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractCommand {

    private final String name;
    private final String description;
    private final String usage;
    private final String permission;
    private final List<String> aliases;
    private final List<AbstractCommand> subcommands;

    public AbstractCommand(String name, String description, String usage, String permission, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.aliases = aliases;
        this.subcommands = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public List<AbstractCommand> getSubcommands() {
        return subcommands;
    }

    public abstract void execute(CommandSender sender, String[] args);

    public List<String> tabComplete(CommandSender sender, String[] args){
        return Collections.singletonList("test");
    }

    public void addSubcommand(AbstractCommand subcommand) {
        subcommands.add(subcommand);
    }
}
