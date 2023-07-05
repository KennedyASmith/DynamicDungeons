package com.kennedysmithjava.dynamicdungeons.cmd;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class CommandManager implements CommandExecutor {

    private final JavaPlugin plugin;
    private final Map<String, AbstractCommand> commands;

    public CommandManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.commands = new HashMap<>();
    }

    public void registerCommand(AbstractCommand command) {
        commands.put(command.getName().toLowerCase(), command);

        PluginCommand pluginCommand = plugin.getCommand(command.getName());
        if (pluginCommand != null) {

            Permission permissionNode = new Permission(command.getPermission());
            permissionNode.setDefault(PermissionDefault.OP); // Set the default permission level
            plugin.getServer().getPluginManager().addPermission(permissionNode);

            pluginCommand.setExecutor(this);
            pluginCommand.setDescription(command.getDescription());
            pluginCommand.setUsage(command.getUsage());
            pluginCommand.setPermission(command.getPermission());
            pluginCommand.setAliases(command.getAliases());
            pluginCommand.setTabCompleter((sender, bukkitCommand, alias, args) -> {
                if (args.length == 1) {
                    List<String> completions = new ArrayList<>();
                    for (AbstractCommand subcommand : command.getSubcommands()) {
                        if (subcommand.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                            completions.add(subcommand.getName());
                        }
                    }
                    return completions;
                } else if (args.length > 1) {
                    String subcommandName = args[0].toLowerCase();
                    AbstractCommand subcommand = command.getSubcommands().stream()
                            .filter(sc -> sc.getName().equalsIgnoreCase(subcommandName))
                            .findFirst()
                            .orElse(null);

                    if (subcommand != null) {
                        // Tab completion for subcommands - delegate to subcommand's tabComplete() method
                        String[] subcommandArgs = Arrays.copyOfRange(args, 1, args.length);
                        return subcommand.tabComplete(sender, subcommandArgs);
                    }
                }

                return Collections.emptyList();
            });
        }

        for (AbstractCommand subcommand : command.getSubcommands()) {
            registerCommand(subcommand);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] args) {
        String commandName = bukkitCommand.getName().toLowerCase();
        AbstractCommand command = commands.get(commandName);

        if (command != null) {
            if (args.length > 0) {
                String subcommandName = args[0].toLowerCase();
                AbstractCommand subcommand = command.getSubcommands().stream()
                        .filter(sc -> sc.getName().equalsIgnoreCase(subcommandName))
                        .findFirst()
                        .orElse(null);

                if (subcommand != null) {
                    String[] subcommandArgs = Arrays.copyOfRange(args, 1, args.length);
                    subcommand.execute(sender, subcommandArgs);
                    return true;
                }
            }

            command.execute(sender, args);
            return true;
        }

        return false;
    }

    public void deregisterAll(Plugin plugin){
        for (AbstractCommand command : commands.values()) {
            plugin.getServer().getPluginManager().removePermission(command.getPermission());
        }
    }
}
