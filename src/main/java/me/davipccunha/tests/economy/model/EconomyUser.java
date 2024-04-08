package me.davipccunha.tests.economy.model;

import me.davipccunha.tests.economy.api.EconomyType;

public interface EconomyUser {
    Economy getEconomy(EconomyType type);

    void loadEconomies();
}
