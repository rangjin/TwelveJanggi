package com.rangjin.twelvejanggi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GameStatus {

    NEW(0), IN_PROGRESS(1), FINISHED(2);

    private final int value;

}
