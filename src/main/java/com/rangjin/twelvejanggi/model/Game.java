package com.rangjin.twelvejanggi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Game {

    private String gameId;
    private GameStatus gameStatus;
    private Player white;
    private Player black;
    private PlayerType winner;
    private List<Order> orderList;
    private Piece[][] board;

    public Game(Player player) {
//        this.gameId = UUID.randomUUID().toString();
        this.gameId = "aaaa";
        this.white = player;
        this.gameStatus = GameStatus.NEW;
        this.winner = PlayerType.NONE;
    }

}
