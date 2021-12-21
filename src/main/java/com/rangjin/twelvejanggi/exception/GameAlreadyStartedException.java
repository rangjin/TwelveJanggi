package com.rangjin.twelvejanggi.exception;

public class GameAlreadyStartedException extends Exception {

    private final String message;

    public GameAlreadyStartedException() {
        this.message = "Game has already started";
    }

}
