package com.splitwise.splitwise.exception;

public class SplitwiseAppException extends RuntimeException {

    static final long serialVersionUID = -66939L;

    public SplitwiseAppException(final String message) {
        super(message);
    }
}
