package com.example.finsmart.Model;

public class Wallet {
    private String walletId;
    private String name;
    private double balance;

    public Wallet(String walletId, String name, double balance) {
        this.walletId = walletId;
        this.name = name;
        this.balance = balance;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
