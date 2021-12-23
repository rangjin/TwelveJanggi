package com.rangjin.twelvejanggi.service.dto;

import com.rangjin.twelvejanggi.model.player.PlayerType;
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
