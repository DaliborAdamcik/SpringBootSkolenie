package com.example.demo.exc;

public class InvalidNameOrPasswordException extends RuntimeException {

    public InvalidNameOrPasswordException(String message) {
        super(message);
    }

    public InvalidNameOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

}
