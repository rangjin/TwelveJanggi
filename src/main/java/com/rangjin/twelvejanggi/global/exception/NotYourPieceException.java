package com.rangjin.twelvejanggi.global.exception;

import lombok.Getter;

@Getter
public class NotYourPieceException extends Exception {

    private final String message;

    public NotYourPieceException() {
        this.message = "Not Your Piece";
    }

}
