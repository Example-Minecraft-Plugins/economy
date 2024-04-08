package me.davipccunha.tests.economy.command.subcommand;

import org.bukkit.command.CommandSender;

public interface EconomySubCommand {
    boolean execute(CommandSender sender, String label, String[] args);

    String getUsage(String label);
}
