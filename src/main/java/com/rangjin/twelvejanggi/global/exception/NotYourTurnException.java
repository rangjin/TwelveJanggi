package com.rangjin.twelvejanggi.global.exception;

import lombok.Getter;

@Getter
public class NotYourTurnException extends Exception {

    private final String message;

    public NotYourTurnException() {
        this.message = "Not This Player's Turn";
    }

}
