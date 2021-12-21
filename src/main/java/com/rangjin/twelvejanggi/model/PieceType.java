package com.rangjin.twelvejanggi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PieceType {

    BLANK(0), WANG(1), SANG(2), JANG(3), JA(4), HU(5);

    private final int value;

}
