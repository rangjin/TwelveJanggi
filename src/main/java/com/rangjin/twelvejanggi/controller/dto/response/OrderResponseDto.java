package com.rangjin.twelvejanggi.controller.dto.response;

import com.rangjin.twelvejanggi.game.model.player.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderResponseDto<T> {

    private PlayerType playerType;

    private T data;

}
