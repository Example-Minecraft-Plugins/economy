package me.davipccunha.tests.economy.provider;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.api.EconomyAPI;
import me.davipccunha.tests.economy.cache.EconomyCache;
import me.davipccunha.tests.economy.api.EconomyType;

@RequiredArgsConstructor
public class EconomyProvider implements EconomyAPI {
    private final EconomyCache cache;

    @Override
    public boolean hasAccount(String playerName) {
        return cache.has(playerName);
    }

    @Override
    public double getBalance(String playerName, EconomyType type) {
        if (!hasAccount(playerName)) return 0.0D;
        return cache.get(playerName).getEconomy(type).getBalance();
    }

    @Override
    public void addBalance(String playerName, EconomyType type, double amount) {
        if (!hasAccount(playerName)) return;
        cache.get(playerName).getEconomy(type).addBalance(amount);
    }

    @Override
    public void removeBalance(String playerName, EconomyType type, double amount) {
        if (!hasAccount(playerName)) return;
        cache.get(playerName).getEconomy(type).removeBalance(amount);
    }
}
