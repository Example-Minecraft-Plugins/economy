package me.davipccunha.tests.economy.command;

import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.api.util.EconomyFormatter;
import me.davipccunha.tests.economy.command.subcommand.*;
import me.davipccunha.tests.economy.model.EconomyUser;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import me.davipccunha.utils.cache.RedisCache;
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
        if (args.length == 0 && !(sender instanceof Player)) {
            sender.sendMessage("§cUso: /" + label + " <player>");
            return false;
        }

        // TODO: Should find a better way to handle economy aliases
        if (label.equalsIgnoreCase("money"))
            label = "coins";

        final RedisCache<EconomyUserImpl> cache = plugin.getEconomyCache();
        final EconomyType economyType = EconomyType.valueOf(label.toUpperCase());
        final String user = args.length >= 1 ? args[0] : sender.getName();
        final EconomySubCommand economySubCommand = subCommands.get(user);

        if (economySubCommand == null) {
            final EconomyUser economyUser = cache.get(user);

            if (economyUser == null) {
                sender.sendMessage("§cUsuário não encontrado!");
                return true;
            }

            double balance = economyUser.getEconomy(economyType).getBalance();

            final String formattedBalance = EconomyFormatter.suffixFormat(balance);
            final String message = String.format("§a%s possui atualmente §f%s §a%s.", args.length == 1 ? user : "Você", formattedBalance, label);

            sender.sendMessage(message);

            return true;
        }

        if (!economySubCommand.execute(sender, label, args)) {
            sender.sendMessage("§cUso: " + economySubCommand.getUsage(label));
            return false;
        }

        return true;
    }
}
