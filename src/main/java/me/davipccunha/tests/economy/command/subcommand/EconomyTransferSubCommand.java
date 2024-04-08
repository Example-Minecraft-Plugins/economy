package me.davipccunha.tests.economy.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.api.util.EconomyFormatter;
import me.davipccunha.tests.economy.model.EconomyUser;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class EconomyTransferSubCommand implements EconomySubCommand {
    private final EconomyPlugin plugin;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length != 3) return false;

        if (!(sender instanceof Player)) return false;

        EconomyType economyType = EconomyType.valueOf(label.toUpperCase());

        String target = args[1];

        EconomyUser economySender = plugin.getEconomyCache().get(sender.getName());
        EconomyUser economyTarget = plugin.getEconomyCache().get(target);

        double amount = NumberUtils.toDouble(args[2]);

        if (economyTarget == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return false;
        }

        if (sender.getName().equals(target)) {
            sender.sendMessage("§cVocê não pode transferir para si mesmo.");
            return false;
        }

        if (amount <= 0) {
            sender.sendMessage("§cQuantidade inválida.");
            return false;
        }

        if (economySender.getEconomy(economyType).getBalance() < amount) {
            sender.sendMessage("§cVocê não tem saldo suficiente.");
            return false;
        }

        if (!economySender.getEconomy(economyType).removeBalance(amount)) {
            sender.sendMessage("§cUm erro interno aconteceu. Comunique-o à nossa equipe.");
            return false;
        }

        if (!economyTarget.getEconomy(economyType).addBalance(amount)) {
            sender.sendMessage("§cUm erro interno aconteceu. Comunique-o à nossa equipe.");
            return false;
        }

        String formattedAmount = EconomyFormatter.suffixFormat(amount);
        String message = String.format("§aTransferido §f%s %s §apara §f%s§a.", formattedAmount, label, target);

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
