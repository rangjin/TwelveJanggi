package com.rangjin.twelvejanggi.model.piece;

import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
