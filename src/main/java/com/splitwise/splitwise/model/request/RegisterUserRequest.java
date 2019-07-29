package com.splitwise.splitwise.model.request;

public class RegisterUserRequest {

    private String name;
    private String email;
    private String passwpord;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPasswpord() {
        return passwpord;
    }

    public void setPasswpord(final String passwpord) {
        this.passwpord = passwpord;
    }
}
