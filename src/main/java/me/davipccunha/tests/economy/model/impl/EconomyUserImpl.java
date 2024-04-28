package me.davipccunha.tests.economy.model.impl;

import lombok.Getter;
import me.davipccunha.tests.economy.model.Economy;
import me.davipccunha.tests.economy.api.EconomyType;
import me.davipccunha.tests.economy.model.EconomyUser;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EconomyUserImpl implements EconomyUser {

    private final String name;
    private final Map<EconomyType, EconomyImpl> economies = new HashMap<>();

    public EconomyUserImpl(String name) {
        this.name = name;
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
