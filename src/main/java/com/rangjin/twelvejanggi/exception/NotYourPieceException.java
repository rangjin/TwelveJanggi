package com.rangjin.twelvejanggi.exception;

public class NotYourPieceException extends Exception {

    private final String message;

    public NotYourPieceException() {
        this.message = "Not Your Piece";
    }

}
