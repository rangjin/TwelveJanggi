package com.rangjin.twelvejanggi.model.order;

import com.rangjin.twelvejanggi.model.Pos;
import com.rangjin.twelvejanggi.model.piece.PieceType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SummonOrder extends Order {

    private PieceType summonPieceType;
    private Pos cur;

}
