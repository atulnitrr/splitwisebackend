package com.splitwise.splitwise.model.request;

public class TransDetail {

    private String paidBy;
    private double amount;

    public String getPaidBy() {
        return paidBy;
    }

    public void setPaidBy(final String paidBy) {
        this.paidBy = paidBy;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }
}
