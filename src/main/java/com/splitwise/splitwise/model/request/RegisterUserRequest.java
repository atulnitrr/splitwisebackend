package com.splitwise.splitwise.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class RegisterUserRequest {

    @NotNull(message = "Name can not be empty")
    @Size(min = 2, max = 12, message = "Name should be between 2 to 12 char")
    private String name;

    @NotNull(message = "Email can not be empty")
    @Email(message = "Please enter valid email" )
    private String email;

    @NotNull(message = "password can not be empty")
    @Size(min = 3, max = 8, message = "Password should be 3 to 8 char")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }
}
