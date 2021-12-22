package com.rangjin.twelvejanggi.service.dto;

import com.rangjin.twelvejanggi.model.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConnectRequest {

    private Player player;
    private String gameId;

}
