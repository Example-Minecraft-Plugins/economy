package me.davipccunha.tests.economy.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.api.util.EconomyFormatter;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class EconomyAddSubCommand implements EconomySubCommand {
    private final EconomyPlugin plugin;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("economy.admin")) {
            sender.sendMessage("§cVocê não tem permissão para executar este comando.");
            return true;
        }

        if (args.length < 3) return false;

        final EconomyType economyType = EconomyType.valueOf(label.toUpperCase());

        final String target = args[1];

        final EconomyUserImpl economyUser = plugin.getEconomyCache().get(target);

        if (economyUser == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return false;
        }

        double amount = NumberUtils.toDouble(args[2]);

        if (amount <= 0) {
            sender.sendMessage("§cQuantidade inválida.");
            return false;
        }

        economyUser.getEconomy(economyType).addBalance(amount);

        plugin.getEconomyCache().add(target, economyUser);

        final String formattedAmount = EconomyFormatter.suffixFormat(amount);
        String message = String.format("§aAdicionado §f%s §apara §f%s§a.", formattedAmount, target);
        sender.sendMessage(message);

        return true;
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " adicionar <player> <quantidade>";
    }
}
