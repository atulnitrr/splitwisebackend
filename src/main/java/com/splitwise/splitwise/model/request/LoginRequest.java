package com.splitwise.splitwise.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class LoginRequest {

    @NotNull(message = "email :  should not be empty")
    @Email(message = "email : should be valid ")
    private String email;

    @NotNull(message = "password : should not be empty")
    @Size(min = 3, max = 8, message = "password : should be of length 3 to 8 char")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
