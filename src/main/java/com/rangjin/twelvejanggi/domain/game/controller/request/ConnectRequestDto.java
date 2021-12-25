package com.rangjin.twelvejanggi.domain.game.controller.request;

import com.rangjin.twelvejanggi.domain.game.model.player.Player;
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
