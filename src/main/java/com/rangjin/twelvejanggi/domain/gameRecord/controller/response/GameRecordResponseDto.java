package com.rangjin.twelvejanggi.domain.gameRecord.controller.response;

import com.rangjin.twelvejanggi.domain.gameRecord.entity.GameRecord;
import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import com.rangjin.twelvejanggi.domain.orderRecord.controller.response.OrderRecordResponseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class GameRecordResponseDto {

    private String gameId;

    private String white;

    private String black;

    private PlayerType winner;

    private List<OrderRecordResponseDto> orderList;

    public GameRecordResponseDto(GameRecord gameRecord) {
        this.gameId = gameRecord.getGameId();
        this.white = gameRecord.getWhite().getUsername();
        this.black = gameRecord.getBlack().getUsername();
        this.winner = gameRecord.getWinner();
        this.orderList = gameRecord.getOrderList().stream().map(OrderRecordResponseDto::new)
                .collect(Collectors.toList());
    }

}
