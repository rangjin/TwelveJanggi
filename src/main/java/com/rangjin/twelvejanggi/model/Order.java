package com.rangjin.twelvejanggi.model;

import com.rangjin.twelvejanggi.model.piece.PieceType;
import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {

    private PlayerType playerType;

    private PieceType pieceType;

    private Pos pre;

    private Pos next;

}
