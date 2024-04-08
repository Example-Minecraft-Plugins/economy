package me.davipccunha.tests.economy.cache;

import me.davipccunha.tests.economy.model.EconomyUser;

import java.util.HashMap;
import java.util.Map;

public class EconomyCache {
    private final Map<String, EconomyUser> users = new HashMap<>();

    public void add(String name, EconomyUser user) {
        users.put(name.toLowerCase(), user);
    }

    public EconomyUser get(String name) {
        return users.get(name.toLowerCase());
    }

    public void remove(String name) {
        users.remove(name.toLowerCase());
    }

    public boolean has(String name) {
        return users.containsKey(name.toLowerCase());
    }
}
