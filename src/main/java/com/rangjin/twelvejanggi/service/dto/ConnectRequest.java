package com.rangjin.twelvejanggi.service.dto;

import com.rangjin.twelvejanggi.model.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectRequest {

    private Player player;
    private String gameId;

}
