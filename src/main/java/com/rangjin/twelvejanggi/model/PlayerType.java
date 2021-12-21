package com.rangjin.twelvejanggi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlayerType {

    NONE(0), WHITE(1), BLACK(2);

    private final int value;

}
