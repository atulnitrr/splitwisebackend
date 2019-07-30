package com.splitwise.splitwise.exception;

import java.util.Date;


public class ErrorMessage {

    private String message;
    private Date date;

    public ErrorMessage() {
    }

    public ErrorMessage(final String message, final Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }
}
