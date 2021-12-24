package com.rangjin.twelvejanggi.service.dto;

import com.rangjin.twelvejanggi.domain.GameRecord;
import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class GameRecordDto {

    private Long id;

    private String gameId;

    private String white;

    private String black;

    private PlayerType winner;

    private List<OrderRecordDto> orderList;

    public GameRecordDto(GameRecord gameRecord) {
        this.id = gameRecord.getId();
        this.gameId = gameRecord.getGameId();
        this.white = gameRecord.getWhite();
        this.black = gameRecord.getBlack();
        this.winner = gameRecord.getWinner();
        this.orderList = gameRecord.getOrderList().stream().map(OrderRecordDto::new).collect(Collectors.toList());
    }

}
