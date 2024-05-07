package me.davipccunha.tests.economy.model;

public interface Economy {
    void addBalance(double amount);

    void removeBalance(double amount);

    double getBalance();

    void setBalance(double amount);

    boolean hasBalance(double amount);
}
