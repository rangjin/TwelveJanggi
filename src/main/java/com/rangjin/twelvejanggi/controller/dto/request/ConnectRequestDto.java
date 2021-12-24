package com.rangjin.twelvejanggi.controller.dto.request;

import com.rangjin.twelvejanggi.game.model.player.Player;
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
