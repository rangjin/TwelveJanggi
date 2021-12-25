package com.rangjin.twelvejanggi.domain.game.model;

import com.rangjin.twelvejanggi.domain.game.model.piece.PieceType;
import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class Order {

    private PlayerType playerType;

    private PieceType pieceType;

    private Pos pre;

    private Pos next;

}
