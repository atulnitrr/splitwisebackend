package com.splitwise.splitwise.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class RegisterUserRequest {

    @NotNull(message = "name : should not be empty")
    @Size(min = 2, max = 12, message = "name : should be of length 2 to 12 char")
    private String name;

    @NotNull(message = "email :  should not be empty")
    @Email(message = "email : should be valid" )
    private String email;

    @NotNull(message = "password : should not be empty")
    @Size(min = 3, max = 8, message = "password : should be of length 3 to 8 char")
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
