package com.rangjin.twelvejanggi.game.exception;

import lombok.Getter;

@Getter
public class DoNotHavePieceException extends Exception {

    private final String message;

    public DoNotHavePieceException() {
        this.message = "You don't Have Piece";
    }

}
