package com.example.finsmart.Model;


import java.util.Date;

public class Transaction {
    public enum TransactionType {
        PAYMENT, DEPOSIT
    }

    private String belongTo;
    private Date date;
    private String name;
    private TransactionType type;

    private boolean isIncome;
    private String amount;
    private int icon;

    public Transaction(String belongTo, Date date, String name, TransactionType type, boolean isIncome, String amount, int icon) {
        this.belongTo = belongTo;
        this.date = date;
        this.name = name;
        this.type = type;
        this.isIncome = isIncome;
        this.amount = amount;
        this.icon = icon;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
