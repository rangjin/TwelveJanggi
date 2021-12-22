package com.rangjin.twelvejanggi.service;

import com.rangjin.twelvejanggi.exception.*;
import com.rangjin.twelvejanggi.model.*;
import com.rangjin.twelvejanggi.model.game.Game;
import com.rangjin.twelvejanggi.model.game.GameStatus;
import com.rangjin.twelvejanggi.model.order.MoveOrder;
import com.rangjin.twelvejanggi.model.piece.Piece;
import com.rangjin.twelvejanggi.model.piece.PieceType;
import com.rangjin.twelvejanggi.model.player.Player;
import com.rangjin.twelvejanggi.model.player.PlayerType;
import com.rangjin.twelvejanggi.storage.GameStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GameService {

    static final int[][][] DIR = {
            {},
            {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0,   1}, {-1, -1}, {-1, 0}, {-1, -1}},
            {{1, -1}, {1, 1}, {-1, -1}, {-1, 1}},
            {{1, 0}, {0, -1}, {0, 1}, {-1, 0}},
            {{1, 0}},
            {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}}
    };

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
        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameStorage.getInstance().setGame(game);

        return game;
    }

    public Game move(MoveOrder order) throws GameNotFoundException, NotYourTurnException, CouldNotMoveException {
        String gameId = order.getGameId();
        if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
            throw new GameNotFoundException();
        }

        Game game = GameStorage.getInstance().getGame(gameId);

        Pos pre = order.getPre();
        checkHighlighted(game, pre);

        Pos next = order.getNext();
        movePiece(game, pre, next);

        game.setWinner(checkGame(game));
        if (game.getWinner() != PlayerType.NONE) {
            log.info("Game Finished : {}", game.getGameId() + ", " + game.getWinner());
            game.setGameStatus(GameStatus.FINISHED);
            GameStorage.getInstance().removeGame(game.getGameId());
        } else {
            GameStorage.getInstance().setGame(game);
        }

        return game;
    }

    public void checkHighlighted(Game game, Pos cur) throws NotYourTurnException, CouldNotMoveException {
        Piece[][] board = game.getBoard();
        PlayerType player = board[cur.x][cur.y].getPlayerType();

        if (player != game.getTurn()) {
            // 이동하고자 하는 기물이 해당 플레이어의 기물이 아니거나 빈 공간일 경우
            throw new NotYourTurnException();
        }

        PieceType piece = board[cur.x][cur.y].getPieceType();
        int cnt = 0;

        for (int i = 0; i < DIR[piece.getValue()].length; i++) {
            // BLACK은 x축에 -1을 곱해서 이동
            int x = cur.x + (player == PlayerType.BLACK ? -1 : 1) * DIR[piece.getValue()][i][0];
            int y = cur.y + DIR[piece.getValue()][i][1];

            if (x >= 0 && x < 4 && y >= 0 && y < 3 && board[x][y].getPlayerType() != player) {
                board[x][y].setHighlighted(true);
                cnt++;
            }
        }

        if (cnt == 0) {
            // 해당 말이 이동할 수 있는 공간이 없는 경우
            throw new CouldNotMoveException();
        }
    }

    public void movePiece(Game game, Pos pre, Pos next) throws CouldNotMoveException {
        Piece[][] board = game.getBoard();

        if (next.x < 0 || next.x > 3 || next.y < 0 || next.y > 3 || !board[next.x][next.y].isHighlighted()) {
            // 이동하고자 하는 장소가 보드 바깥이거나 해당 말이 이동할 수 없는 공간일 경우
            throw new CouldNotMoveException();
        }

        // 상대편 말을 잡았을 경우 자신의 수중에 넣음
        if (board[next.x][next.y].getPieceType() != PieceType.BLANK) {
            board[next.x][next.y].setPlayerType(game.getTurn());
            board[next.x][next.y].setHighlighted(false);
            if (game.getTurn() == PlayerType.WHITE) {
                game.getWhitePieces().add(board[next.x][next.y]);
            } else {
                game.getBlackPieces().add(board[next.x][next.y]);
            }
        }

        board[next.x][next.y] = board[pre.x][pre.y];
        board[pre.x][pre.y] = new Piece(PlayerType.NONE, PieceType.BLANK, false);

        // JA가 상대편 진영 맨 끝에 도달했을 경우 HU로 승격
        if ((next.x == 0 || next.x == 3) && board[next.x][next.y].getPieceType() == PieceType.JA) {
            board[next.x][next.y] = new Piece(board[next.x][next.y].getPlayerType(), PieceType.HU, false);
        }

        // 기물 이동이 끝났으므로 하이라이트 OFF
        highlightOff(board);
    }

    public void highlightOff(Piece[][] board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setHighlighted(false);
            }
        }
    }

    PlayerType checkGame(Game game) {
        Piece[][] board = game.getBoard();
        boolean whiteWangExisted = false, blackWangExisted = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getPieceType() == PieceType.WANG) {
                    if (board[i][j].getPlayerType() == PlayerType.WHITE) {
                        whiteWangExisted = true;

                        if (i == 3) {
                            if (game.getState() == PlayerType.WHITE) {
                                return PlayerType.WHITE;
                            }
                            game.setState(PlayerType.WHITE);
                        }
                    } else {
                        blackWangExisted = true;

                        if (i == 0) {
                            if (game.getState() == PlayerType.BLACK) {
                                return PlayerType.BLACK;
                            }
                            game.setState(PlayerType.BLACK);
                        }
                    }
                }
            }
        }

        if (!whiteWangExisted) {
            // WHITE WANG이 잡힌 경우
            return PlayerType.BLACK;
        } else if (!blackWangExisted) {
            // BLACK WANG이 잡힌 경우
            return PlayerType.WHITE;
        }

        if (game.getTurn() == PlayerType.WHITE) {
            game.setTurn(PlayerType.BLACK);
        } else {
            game.setTurn(PlayerType.WHITE);
        }

        return PlayerType.NONE;
    }

}
