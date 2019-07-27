package com.splitwise.splitwise.model.request;

public class TransDetail {

    private String paidBy;
    private double amount;

    public TransDetail() {
    }

    public TransDetail(final String paidBy, final double amount) {
        this.paidBy = paidBy;
        this.amount = amount;
    }

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
