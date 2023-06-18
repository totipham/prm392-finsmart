package com.example.finsmart.Model;


public class Transaction {
    public enum TransactionType {
        PAYMENT, DEPOSIT
    }

    private String id;
    private String name;
    private TransactionType type;

    private boolean isIncome;
    private String amount;
    private int icon;

    public Transaction(String id, String name, TransactionType type, boolean isIncome, String amount, int icon) {
        ;
        this.id = id;
        this.name = name;
        this.type = type;
        this.isIncome = isIncome;
        this.amount = amount;
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
