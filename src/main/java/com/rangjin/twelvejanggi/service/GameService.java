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
        GameStorage.getInstance().setGame(game);

        return game;
    }

}
