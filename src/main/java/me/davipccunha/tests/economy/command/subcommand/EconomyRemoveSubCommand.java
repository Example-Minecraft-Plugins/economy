package me.davipccunha.tests.economy.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.api.util.EconomyFormatter;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import me.davipccunha.utils.messages.ErrorMessages;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class EconomyRemoveSubCommand implements EconomySubCommand {

    private final EconomyPlugin plugin;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("economy.admin")) {
            sender.sendMessage(ErrorMessages.NO_PERMISSION.getMessage());
            return true;
        }

        if (args.length < 3) return false;

        final EconomyType economyType = EconomyType.valueOf(label.toUpperCase());

        final String target = args[1].toLowerCase();

        final EconomyUserImpl economyUser = plugin.getEconomyCache().get(target);

        final double amount = NumberUtils.toDouble(args[2]);

        if (economyUser == null) {
            sender.sendMessage(ErrorMessages.PLAYER_NOT_FOUND.getMessage());
            return true;
        }

        if (amount <= 0) {
            sender.sendMessage(ErrorMessages.INVALID_AMOUNT.getMessage());
            return true;
        }

        economyUser.getEconomy(economyType).removeBalance(amount);

        plugin.getEconomyCache().add(target, economyUser);

        final String formattedAmount = EconomyFormatter.suffixFormat(amount);
        final String message = String.format("§aRemovidos §f%s %s §ade §f%s§a.", formattedAmount, economyType, economyUser.getUsername());
        sender.sendMessage(message);

        return true;
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " remover <jogador> <quantidade>";
    }
}
