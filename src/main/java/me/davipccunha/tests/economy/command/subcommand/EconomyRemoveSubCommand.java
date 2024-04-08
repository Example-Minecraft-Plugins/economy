package me.davipccunha.tests.economy.command.subcommand;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.EconomyPlugin;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.api.util.EconomyFormatter;
import me.davipccunha.tests.economy.model.EconomyUser;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

@RequiredArgsConstructor
public class EconomyRemoveSubCommand implements EconomySubCommand {

    private final EconomyPlugin plugin;

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length != 3) return false;

        EconomyType economyType = EconomyType.valueOf(label.toUpperCase());

        String target = args[1];

        EconomyUser economyUser = plugin.getEconomyCache().get(target);

        double amount = NumberUtils.toDouble(args[2]);

        if (economyUser == null) {
            sender.sendMessage("§cJogador não encontrado.");
            return false;
        }

        if (amount <= 0) {
            sender.sendMessage("§cQuantidade inválida.");
            return false;
        }

        if (!economyUser.getEconomy(economyType).removeBalance(amount)) {
            sender.sendMessage("§cUm erro interno aconteceu. Comunique-o à nossa equipe.");
            return false;
        }

        String formattedAmount = EconomyFormatter.suffixFormat(amount);
        String message = String.format("§aRemovido §f%s %s §ade §f%s§a.", formattedAmount, label, target);
        sender.sendMessage(message);

        return true;
    }

    @Override
    public String getUsage(String label) {
        return "/" + label + " remover <player> <quantidade>";
    }
}
