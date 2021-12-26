package com.rangjin.twelvejanggi.global.exception;

import lombok.Getter;

@Getter
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException() {
        super("Player doesn't exist");
    }

}
