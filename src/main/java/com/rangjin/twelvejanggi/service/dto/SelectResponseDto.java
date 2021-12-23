package com.rangjin.twelvejanggi.service.dto;

import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectResponseDto<T> {

    private PlayerType playerType;
    private T data;

}
