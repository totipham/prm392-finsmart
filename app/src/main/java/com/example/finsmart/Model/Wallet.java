package com.example.finsmart.Model;

public class Wallet {
    private String walletId;
    private String name;
    private String balance;

    public Wallet(String walletId, String name, String balance) {
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
