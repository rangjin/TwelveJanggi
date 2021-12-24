package com.rangjin.twelvejanggi.game.exception;

import lombok.Getter;

@Getter
public class GameNotFoundException extends Exception {

    private final String message;

    public GameNotFoundException() {
        this.message = "Game is not exist";
    }

}
