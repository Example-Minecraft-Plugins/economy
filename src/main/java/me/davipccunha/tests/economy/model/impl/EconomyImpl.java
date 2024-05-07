package me.davipccunha.tests.economy.model.impl;

import lombok.Getter;
import me.davipccunha.tests.economy.model.Economy;

@Getter
public class EconomyImpl implements Economy {
    private double balance;

    @Override
    public void addBalance(double amount) {
        if (amount <= 0) return;
        this.balance += amount;
    }

    @Override
    public void removeBalance(double amount) {
        if (amount <= 0) return;

        this.balance = Math.max(0, this.balance - amount);
    }

    @Override
    public void setBalance(double amount) {
        if (amount < 0) return;

        this.balance = amount;
    }

    @Override
    public boolean hasBalance(double amount) {
        return this.balance >= amount;
    }
}
