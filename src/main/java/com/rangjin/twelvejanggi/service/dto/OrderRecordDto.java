package com.rangjin.twelvejanggi.service.dto;

import com.rangjin.twelvejanggi.domain.OrderRecord;
import com.rangjin.twelvejanggi.model.piece.PieceType;
import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRecordDto {

    private Long id;

    private PlayerType playerType;

    private PieceType pieceType;

    private int preX;

    private int preY;

    private int nextX;

    private int nextY;

    private String gameRecord;

    public OrderRecordDto(OrderRecord orderRecord) {
        this.id = orderRecord.getId();
        this.playerType = orderRecord.getPlayerType();
        this.pieceType = orderRecord.getPieceType();
        this.preX = orderRecord.getPreX();
        this.preY = orderRecord.getPreY();
        this.nextX = orderRecord.getNextX();
        this.nextY = orderRecord.getNextY();
        this.gameRecord = orderRecord.getGameRecord().getGameId();
    }

}
