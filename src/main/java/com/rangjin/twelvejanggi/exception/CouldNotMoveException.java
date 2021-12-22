package com.rangjin.twelvejanggi.exception;

public class CouldNotMoveException extends Exception {

    private final String message;

    public CouldNotMoveException() {
        this.message = "Could not Move";
    }

}
