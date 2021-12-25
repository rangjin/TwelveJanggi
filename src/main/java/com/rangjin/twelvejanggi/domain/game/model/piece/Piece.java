package com.rangjin.twelvejanggi.domain.game.model.piece;

import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Piece {

    private PlayerType playerType;

    private PieceType pieceType;

    private boolean pointed;

    private boolean highlighted;

    public Piece(PlayerType playerType, PieceType pieceType) {
        this.playerType = playerType;
        this.pieceType = pieceType;
        this.pointed = false;
        this.highlighted = false;
    }

}
