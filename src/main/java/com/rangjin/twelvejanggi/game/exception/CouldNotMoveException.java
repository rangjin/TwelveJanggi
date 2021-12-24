package com.rangjin.twelvejanggi.game.exception;

import lombok.Getter;

@Getter
public class CouldNotMoveException extends Exception {

    private final String message;

    public CouldNotMoveException() {
        this.message = "Could not Move";
    }

}
