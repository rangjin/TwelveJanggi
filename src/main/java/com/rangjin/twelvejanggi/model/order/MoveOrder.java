package com.rangjin.twelvejanggi.model.order;

import com.rangjin.twelvejanggi.model.Pos;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MoveOrder extends Order {

    private Pos pre;
    private Pos next;

}
