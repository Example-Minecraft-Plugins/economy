package me.davipccunha.tests.economy.provider;

import lombok.RequiredArgsConstructor;
import me.davipccunha.tests.economy.api.EconomyAPI;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.model.impl.EconomyUserImpl;
import me.davipccunha.utils.cache.RedisCache;

@RequiredArgsConstructor
public class EconomyProvider implements EconomyAPI {
    private final RedisCache<EconomyUserImpl> cache;

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
        EconomyUserImpl economyUser = cache.get(playerName);
        economyUser.getEconomy(type).addBalance(amount);

        cache.add(playerName, economyUser);
    }

    @Override
    public void removeBalance(String playerName, EconomyType type, double amount) {
        if (!hasAccount(playerName)) return;
        EconomyUserImpl economyUser = cache.get(playerName);
        economyUser.getEconomy(type).removeBalance(amount);

        cache.add(playerName, economyUser);
    }
}
