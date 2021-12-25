package com.rangjin.twelvejanggi.domain.game.model;

import lombok.ToString;

@ToString
public class Pos {

    public int x, y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
