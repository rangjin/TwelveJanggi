package com.rangjin.twelvejanggi.domain;

import com.rangjin.twelvejanggi.game.model.Order;
import com.rangjin.twelvejanggi.game.model.piece.PieceType;
import com.rangjin.twelvejanggi.game.model.player.PlayerType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Accessors(chain = true)
@NoArgsConstructor
public class OrderRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private PlayerType playerType;

    private PieceType pieceType;

    private int preX;

    private int preY;

    private int nextX;

    private int nextY;

    @ManyToOne
    private GameRecord gameRecord;

    public OrderRecord(Order dto, GameRecord gameRecord) {
        this.playerType = dto.getPlayerType();
        this.pieceType = dto.getPieceType();
        this.preX = dto.getPre().x;
        this.preY = dto.getPre().y;
        this.nextX = dto.getNext().x;
        this.nextY = dto.getNext().y;
        this.gameRecord = gameRecord;
    }

}
