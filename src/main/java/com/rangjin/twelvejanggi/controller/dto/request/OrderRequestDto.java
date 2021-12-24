package com.rangjin.twelvejanggi.controller.dto.request;

import com.rangjin.twelvejanggi.game.model.player.PlayerType;
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
