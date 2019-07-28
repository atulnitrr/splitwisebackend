package com.splitwise.splitwise.model.response;

public class UserBalanceResponse {

    private String name;
    private Double amout;

    public UserBalanceResponse() {
    }

    public UserBalanceResponse(final String name, final Double amout) {
        this.name = name;
        this.amout = amout;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Double getAmout() {
        return amout;
    }

    public void setAmout(final Double amout) {
        this.amout = amout;
    }
}
