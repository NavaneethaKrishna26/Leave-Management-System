package com.lms.demo.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String username) {
        super("User already exists: " + username);
    }

}
