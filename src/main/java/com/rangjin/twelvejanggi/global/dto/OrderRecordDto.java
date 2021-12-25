package com.rangjin.twelvejanggi.global.dto;

import com.rangjin.twelvejanggi.domain.orderRecord.entity.OrderRecord;
import com.rangjin.twelvejanggi.domain.game.model.piece.PieceType;
import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRecordDto {

    private PlayerType playerType;

    private PieceType pieceType;

    private int preX;

    private int preY;

    private int nextX;

    private int nextY;

    public OrderRecordDto(OrderRecord orderRecord) {
        this.playerType = orderRecord.getPlayerType();
        this.pieceType = orderRecord.getPieceType();
        this.preX = orderRecord.getPreX();
        this.preY = orderRecord.getPreY();
        this.nextX = orderRecord.getNextX();
        this.nextY = orderRecord.getNextY();
    }

}
