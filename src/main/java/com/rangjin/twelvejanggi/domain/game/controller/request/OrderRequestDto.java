package com.rangjin.twelvejanggi.domain.game.controller.request;

import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderRequestDto<T> {

    private String gameId;

    private PlayerType playerType;

    private T data;

}
