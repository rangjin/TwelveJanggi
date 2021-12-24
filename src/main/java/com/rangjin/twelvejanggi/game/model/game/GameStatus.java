package com.rangjin.twelvejanggi.game.model.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameStatus {

    NEW(0), IN_PROGRESS(1), FINISHED(2);

    private final int value;

}
