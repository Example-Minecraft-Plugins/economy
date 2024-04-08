package me.davipccunha.tests.economy.model.impl;

import lombok.Getter;
import me.davipccunha.tests.economy.model.Economy;

@Getter
public class EconomyImpl implements Economy {
    private double balance;

    @Override
    public boolean addBalance(double amount) {
        if (amount < 0) return false;

        this.balance += amount;
        return true;
    }

    @Override
    public boolean removeBalance(double amount) {
        if (amount < 0) return false;

        this.balance = Math.max(0, this.balance - amount);
        return true;
    }

    @Override
    public boolean setBalance(double amount) {
        if (amount < 0) return false;

        this.balance = amount;;
        return true;
    }

    @Override
    public boolean hasBalance(double amount) {
        return this.balance >= amount;
    }
}
