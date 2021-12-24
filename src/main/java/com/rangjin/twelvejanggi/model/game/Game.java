package com.rangjin.twelvejanggi.model.game;

import com.rangjin.twelvejanggi.model.Order;
import com.rangjin.twelvejanggi.model.piece.Piece;
import com.rangjin.twelvejanggi.model.piece.PieceType;
import com.rangjin.twelvejanggi.model.player.Player;
import com.rangjin.twelvejanggi.model.player.PlayerType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Game {

    private String gameId;
    private GameStatus gameStatus;
    private Player white;
    private Player black;
    private PlayerType turn;
    private PlayerType state;
    private PlayerType winner;
    private Piece[][] board;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;
    private List<Order> orderDtoList;

    public Game(Player player) {
//        this.gameId = UUID.randomUUID().toString();
        this.gameId = "aaaa";
        this.gameStatus = GameStatus.NEW;
        this.white = player;
        this.black = null;
        this.turn = PlayerType.WHITE;
        this.winner = PlayerType.NONE;
        initBoard();
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.orderDtoList = new ArrayList<>();
    }

    public void initBoard() {
        this.board = new Piece[4][3];
        this.board[0][0] = new Piece(PlayerType.WHITE, PieceType.JANG);
        this.board[0][1] = new Piece(PlayerType.WHITE, PieceType.WANG);
        this.board[0][2] = new Piece(PlayerType.WHITE, PieceType.SANG);
        this.board[1][0] = new Piece(PlayerType.NONE, PieceType.BLANK);
        this.board[1][1] = new Piece(PlayerType.WHITE, PieceType.JA);
        this.board[1][2] = new Piece(PlayerType.NONE, PieceType.BLANK);
        this.board[2][0] = new Piece(PlayerType.NONE, PieceType.BLANK);
        this.board[2][1] = new Piece(PlayerType.BLACK, PieceType.JA);
        this.board[2][2] = new Piece(PlayerType.NONE, PieceType.BLANK);
        this.board[3][0] = new Piece(PlayerType.BLACK, PieceType.SANG);
        this.board[3][1] = new Piece(PlayerType.BLACK, PieceType.WANG);
        this.board[3][2] = new Piece(PlayerType.BLACK, PieceType.JANG);
    }

}
