package com.rangjin.twelvejanggi.model.order;

import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Order {

    protected String gameId;
    protected PlayerType playerType;

}
