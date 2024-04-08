package me.davipccunha.tests.economy.api;

public interface EconomyAPI {
    boolean hasAccount(String playerName);

    double getBalance(String playerName, EconomyType type);

    void addBalance(String playerName, EconomyType type, double amount);

    void removeBalance(String playerName, EconomyType type, double amount);
}
