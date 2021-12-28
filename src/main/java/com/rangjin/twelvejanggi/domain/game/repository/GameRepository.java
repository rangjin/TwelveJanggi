package com.rangjin.twelvejanggi.domain.game.repository;

import com.rangjin.twelvejanggi.domain.game.model.game.Game;

import java.util.HashMap;
import java.util.Map;

public class GameRepository {

    private final HashMap<String, Game> games;

    private GameRepository() {
        games = new HashMap<>();
    }

    public static GameRepository getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final GameRepository INSTANCE = new GameRepository();
    }

    public Game getGame(String gameId) {
        return games.get(gameId);
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public void setGame(Game game) {
        games.put(game.getGameId(), game);
    }

    public void removeGame(String gameId) {
        games.remove(gameId);
    }

}
