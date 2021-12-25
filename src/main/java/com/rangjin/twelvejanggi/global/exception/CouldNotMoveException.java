package com.rangjin.twelvejanggi.global.exception;

import lombok.Getter;

@Getter
public class CouldNotMoveException extends Exception {

    private final String message;

    public CouldNotMoveException() {
        this.message = "Could not Move";
    }

}
