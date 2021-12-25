package com.rangjin.twelvejanggi.domain.game.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrderResponseDto<T> {

    private T data;

}
