package com.rangjin.twelvejanggi.service.dto;

import com.rangjin.twelvejanggi.model.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConnectRequestDto<T> {

    private String gameId;
    private Player player;

}