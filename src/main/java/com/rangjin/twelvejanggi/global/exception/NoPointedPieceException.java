package com.rangjin.twelvejanggi.global.exception;

import lombok.Getter;

@Getter
public class NoPointedPieceException extends Throwable {

    private final String message;

    public NoPointedPieceException() {
        this.message = "No Pointed Piece";
    }

}
