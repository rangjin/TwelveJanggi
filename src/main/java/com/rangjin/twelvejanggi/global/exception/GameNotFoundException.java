package com.rangjin.twelvejanggi.global.exception;

import lombok.Getter;

@Getter
public class GameNotFoundException extends Exception {

    private final String message;

    public GameNotFoundException() {
        this.message = "Game is not exist";
    }

}
