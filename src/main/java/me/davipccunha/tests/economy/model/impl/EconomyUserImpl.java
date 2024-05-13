package me.davipccunha.tests.economy.model.impl;

import lombok.Getter;
import me.davipccunha.tests.economy.model.Economy;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.model.EconomyUser;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EconomyUserImpl implements EconomyUser {

    private final String username;
    private final Map<EconomyType, EconomyImpl> economies = new HashMap<>();

    public EconomyUserImpl(String username) {
        this.username = username;
        this.loadEconomies();
    }

    @Override
    public Economy getEconomy(EconomyType type) {
        return economies.get(type);
    }

    @Override
    public void loadEconomies() {
        for (EconomyType type : EconomyType.values()) {
            economies.put(type, new EconomyImpl());
        }
    }
}
