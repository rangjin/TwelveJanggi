package com.rangjin.twelvejanggi.game.exception;

import lombok.Getter;

@Getter
public class GameAlreadyStartedException extends Exception {

    private final String message;

    public GameAlreadyStartedException() {
        this.message = "Game has already started";
    }

}
