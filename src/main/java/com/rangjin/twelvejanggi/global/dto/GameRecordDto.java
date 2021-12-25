package com.rangjin.twelvejanggi.global.dto;

import com.rangjin.twelvejanggi.domain.gameRecord.entity.GameRecord;
import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class GameRecordDto {

    private String gameId;

    private String white;

    private String black;

    private PlayerType winner;

    private List<OrderRecordDto> orderList;

    public GameRecordDto(GameRecord gameRecord) {
        this.gameId = gameRecord.getGameId();
        this.white = gameRecord.getWhite();
        this.black = gameRecord.getBlack();
        this.winner = gameRecord.getWinner();
        this.orderList = gameRecord.getOrderList().stream().map(OrderRecordDto::new).collect(Collectors.toList());
    }

}
