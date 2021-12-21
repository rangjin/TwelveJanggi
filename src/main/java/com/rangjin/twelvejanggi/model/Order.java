package com.rangjin.twelvejanggi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Order {

    private OrderType orderType;
    private int preX;
    private int preY;
    private int nextX;
    private int nextY;

}
