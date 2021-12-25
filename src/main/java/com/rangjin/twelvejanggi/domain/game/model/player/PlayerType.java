package com.rangjin.twelvejanggi.domain.game.model.player;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayerType {

    NONE(0), WHITE(1), BLACK(2);

    private final int value;

}
