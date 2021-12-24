package com.rangjin.twelvejanggi.game.service;

import com.rangjin.twelvejanggi.game.exception.GameAlreadyStartedException;
import com.rangjin.twelvejanggi.game.exception.GameNotFoundException;
import com.rangjin.twelvejanggi.game.model.game.Game;
import com.rangjin.twelvejanggi.game.model.game.GameStatus;
import com.rangjin.twelvejanggi.game.model.player.Player;
import com.rangjin.twelvejanggi.game.storage.GameStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ConnectService {

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
        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);

        return game;
    }

}
