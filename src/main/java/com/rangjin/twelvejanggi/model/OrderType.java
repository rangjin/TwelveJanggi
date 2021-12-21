package com.rangjin.twelvejanggi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {

    MOVE("Move", 0), SUMMON("Summon", 1);

    private final String type;
    private final int value;

}
