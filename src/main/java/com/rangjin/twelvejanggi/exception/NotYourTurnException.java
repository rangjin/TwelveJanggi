package com.rangjin.twelvejanggi.exception;

public class NotYourTurnException extends Exception {

    private final String message;

    public NotYourTurnException() {
        this.message = "Not This Player's Turn";
    }

}
