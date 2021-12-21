package com.rangjin.twelvejanggi.service;

import com.rangjin.twelvejanggi.exception.GameAlreadyStartedException;
import com.rangjin.twelvejanggi.exception.GameNotFoundException;
import com.rangjin.twelvejanggi.model.*;
import com.rangjin.twelvejanggi.storage.GameStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {

    public Game findByGameId(String gameId) {
        return GameStorage.getInstance().getGame(gameId);
    }

    public Game create(Player player) {
        Game game = new Game(player);
        GameStorage.getInstance().setGame(game);

        return game;
    }

    public Game connect(Player player, String gameId) throws GameNotFoundException, GameAlreadyStartedException {
        if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
            throw new GameNotFoundException();
        }

        Game game = GameStorage.getInstance().getGame(gameId);

        if (game.getBlack() != null) {
            throw new GameAlreadyStartedException();
        }

        game.setBlack(player);
        initGame(game);
        GameStorage.getInstance().setGame(game);

        return game;
    }

    public void initGame(Game game) {
        Piece[][] board = new Piece[4][3];
        board[0][0] = new Piece(PlayerType.WHITE, PieceType.JANG);
        board[0][1] = new Piece(PlayerType.WHITE, PieceType.WANG);
        board[0][2] = new Piece(PlayerType.WHITE, PieceType.SANG);
        board[1][0] = new Piece(PlayerType.NONE, PieceType.BLANK);
        board[1][1] = new Piece(PlayerType.WHITE, PieceType.JA);
        board[1][2] = new Piece(PlayerType.NONE, PieceType.BLANK);
        board[2][0] = new Piece(PlayerType.NONE, PieceType.BLANK);
        board[2][1] = new Piece(PlayerType.BLACK, PieceType.JA);
        board[2][2] = new Piece(PlayerType.NONE, PieceType.BLANK);
        board[3][0] = new Piece(PlayerType.BLACK, PieceType.SANG);
        board[3][1] = new Piece(PlayerType.BLACK, PieceType.WANG);
        board[3][2] = new Piece(PlayerType.BLACK, PieceType.JANG);
        game.setBoard(board);
    }

}
