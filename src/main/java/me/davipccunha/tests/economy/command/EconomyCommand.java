package me.davipccunha.tests.economy.command;

import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.api.util.EconomyFormatter;
import me.davipccunha.tests.economy.cache.EconomyCache;
import me.davipccunha.tests.economy.command.subcommand.*;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.model.EconomyUser;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class EconomyCommand implements CommandExecutor {
    private final EconomyPlugin plugin;
    private final Map<String, EconomySubCommand> subCommands = new HashMap<>();

    public EconomyCommand(EconomyPlugin plugin) {
        this.plugin = plugin;
        this.subCommands.put("adicionar", new EconomyAddSubCommand(plugin));
        this.subCommands.put("remover", new EconomyRemoveSubCommand(plugin));
        this.subCommands.put("definir", new EconomySetSubCommand(plugin));
        this.subCommands.put("transferir", new EconomyTransferSubCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && !(sender instanceof Player)) return false;

        EconomyType economyType = EconomyType.valueOf(label.toUpperCase());

        String user = args.length == 1 ? args[0] : sender.getName();

        EconomyCache cache = plugin.getEconomyCache();

        String senderName = sender.getName();

        if (!cache.has(senderName)) {
            cache.add(senderName, new EconomyUserImpl(senderName));
        }

        EconomyUser economyUser = cache.get(user);

        if (economyUser == null) {
            sender.sendMessage("§cUsuário não encontrado!");
            return false;
        }

        if (args.length <= 1) {
            double balance = economyUser.getEconomy(economyType).getBalance();

            final String formattedBalance = EconomyFormatter.suffixFormat(balance);
            String message = String.format("§a%s possui atualmente §f%s §a%s.", args.length == 1 ? user : "Você", formattedBalance, label);

            sender.sendMessage(message);

            return true;
        }

        String subCommand = args[0].toLowerCase();

        EconomySubCommand economySubCommand = subCommands.get(subCommand);

        if (economySubCommand == null) {
            sender.sendMessage("§cSubcomando não encontrado!");

            return false;
        }

        if (!economySubCommand.execute((Player) sender, label, args)) {
            sender.sendMessage("§cUso: " + economySubCommand.getUsage(label));
            return false;
        }

        return true;
    }
}
