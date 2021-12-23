package com.rangjin.twelvejanggi.exception;

public class DoNotHavePieceException extends Exception {

    private final String message;

    public DoNotHavePieceException() {
        this.message = "You don't Have Piece";
    }

}
