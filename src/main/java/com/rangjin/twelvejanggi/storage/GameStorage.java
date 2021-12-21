package com.rangjin.twelvejanggi.storage;

import com.rangjin.twelvejanggi.model.Game;

import java.util.HashMap;
import java.util.Map;

public class GameStorage {

    private HashMap<String, Game> games;

    private GameStorage() {
        games = new HashMap<>();
    }

    public static GameStorage getInstance() {
        return LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        private static final GameStorage INSTANCE = new GameStorage();
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
