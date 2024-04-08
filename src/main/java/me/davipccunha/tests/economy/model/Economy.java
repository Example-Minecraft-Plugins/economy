package me.davipccunha.tests.economy.model;

public interface Economy {
    boolean addBalance(double amount);

    boolean removeBalance(double amount);

    double getBalance();

    boolean setBalance(double amount);

    boolean hasBalance(double amount);
}
