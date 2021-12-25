package com.rangjin.twelvejanggi.global.exception;

import lombok.Getter;

@Getter
public class CouldNotSummonException extends Exception {

    private final String message;

    public CouldNotSummonException() {
        this.message = "Could not Summon";
    }

}
