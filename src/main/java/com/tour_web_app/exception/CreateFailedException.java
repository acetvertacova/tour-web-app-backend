package com.tour_web_app.exception;

public class CreateFailedException extends RuntimeException {
    public CreateFailedException(String message) {
        super(message);
    }
}
