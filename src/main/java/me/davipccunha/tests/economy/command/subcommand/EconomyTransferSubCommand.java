package me.davipccunha.tests.economy.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.api.util.EconomyFormatter;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class EconomyTransferSubCommand implements EconomySubCommand {
    private final EconomyPlugin plugin;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar este comando.");
            return true;
        }

        final EconomyType economyType = EconomyType.valueOf(label.toUpperCase());

        if (economyType.equals(EconomyType.CASH)) {
            sender.sendMessage("§cVocê não pode transferir cash");
            return true;
        }

        if (args.length < 3) return false;

        final String target = args[1];

        final EconomyUserImpl economySender = plugin.getEconomyCache().get(sender.getName());
        final EconomyUserImpl economyTarget = plugin.getEconomyCache().get(target);

        final double amount = NumberUtils.toDouble(args[2]);

        if (economyTarget == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return true;
        }

        if (sender.getName().equals(target)) {
            sender.sendMessage("§cVocê não pode transferir para si mesmo.");
            return true;
        }

        if (amount <= 0) {
            sender.sendMessage("§cQuantidade inválida.");
            return false;
        }

        if (economySender.getEconomy(economyType).getBalance() < amount) {
            sender.sendMessage("§cVocê não tem saldo suficiente.");
            return true;
        }

        economySender.getEconomy(economyType).removeBalance(amount);
        economyTarget.getEconomy(economyType).addBalance(amount);

        plugin.getEconomyCache().add(sender.getName(), economySender);
        plugin.getEconomyCache().add(target, economyTarget);

        final String formattedAmount = EconomyFormatter.suffixFormat(amount);
        final String message = String.format("§aTransferido §f%s %s §apara §f%s§a.", formattedAmount, label, target);

        sender.sendMessage(message);

        final Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null)
            targetPlayer.sendMessage(String.format("§aRecebido §f%s %s §ade §f%s§a.", formattedAmount, label, sender.getName()));

        return true;
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " transferir <player> <quantidade>";
    }
}
