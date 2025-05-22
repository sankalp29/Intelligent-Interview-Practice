package com.codinginterview.interview_platform.auth.exceptions;

public class EmailAlreadyExistsException extends Exception {
    private final static String MESSAGE = "This email is already registered. Please head to the login page.";
    
    public EmailAlreadyExistsException() {
        super(MESSAGE);
    }
}
