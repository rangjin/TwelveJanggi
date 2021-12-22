package com.rangjin.twelvejanggi.exception;

public class CouldNotSummonException extends Exception {

    private final String message;

    public CouldNotSummonException() {
        this.message = "Could not Summon";
    }

}
