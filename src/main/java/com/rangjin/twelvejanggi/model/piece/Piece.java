package com.rangjin.twelvejanggi.model.piece;

import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Piece {

    private PlayerType playerType;

    private PieceType pieceType;

    private boolean highlighted;

}
