package com.project.MyBank.domain.exceptions;

public class CookieNotFoundException extends RuntimeException {
    public CookieNotFoundException(String message) {
        super(message);
    }
}
