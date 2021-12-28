package com.rangjin.twelvejanggi.domain.game.service;

import com.rangjin.twelvejanggi.domain.game.model.Order;
import com.rangjin.twelvejanggi.domain.game.model.Pos;
import com.rangjin.twelvejanggi.domain.game.model.game.Game;
import com.rangjin.twelvejanggi.domain.game.model.game.GameStatus;
import com.rangjin.twelvejanggi.domain.game.model.piece.Piece;
import com.rangjin.twelvejanggi.domain.game.model.piece.PieceType;
import com.rangjin.twelvejanggi.domain.game.model.player.Player;
import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import com.rangjin.twelvejanggi.domain.gameRecord.service.GameRecordService;
import com.rangjin.twelvejanggi.domain.game.repository.GameRepository;
import com.rangjin.twelvejanggi.domain.player.entity.PlayerEntity;
import com.rangjin.twelvejanggi.global.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

    private final GameRecordService gameRecordService;

    public Game create() {
        PlayerEntity player = (PlayerEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Game game = new Game(new Player(player.getUsername()));
        GameRepository.getInstance().setGame(game);

        log.info("Start Game : {}", player.getUsername());

        return game;
    }

    public Game connect(String gameId) throws GameNotFoundException, GameAlreadyStartedException {
        Game game = GameRepository.getInstance().getGame(gameId);

        if (game == null) {
            throw new GameNotFoundException();
        } else if (game.getBlack() != null) {
            throw new GameAlreadyStartedException();
        }

        PlayerEntity player = (PlayerEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        game.setBlack(new Player(player.getUsername()));
        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameRepository.getInstance().setGame(game);

        log.info("Connect Game : {}, {}", gameId, player.getUsername());

        return game;
    }

    static final int[][][] DIR = {
            {},
            {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0,   1}, {-1, -1}, {-1, 0}, {-1, -1}},
            {{1, -1}, {1, 1}, {-1, -1}, {-1, 1}},
            {{1, 0}, {0, -1}, {0, 1}, {-1, 0}},
            {{1, 0}},
            {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}}
    };

    public List<Pos> highlight(String gameId, PlayerType playerType, Pos cur)
            throws NotYourTurnException, GameNotFoundException, CouldNotMoveException, NotYourPieceException {
        Game game = GameRepository.getInstance().getGame(gameId);
        checkError(game, playerType);

        Piece[][] board = game.getBoard();
        List<Piece> pieceList = (playerType == PlayerType.WHITE ? game.getWhitePieces() : game.getBlackPieces());

        resetBagPoint(pieceList);
        resetBoardPoint(board);

        List<Pos> highlightedList = new ArrayList<>();
        if (board[cur.x][cur.y].getPlayerType() != playerType) throw new NotYourPieceException();
        PieceType piece = board[cur.x][cur.y].getPieceType();

        // 해당 좌표의 기물이 이동 가능한 좌표 체크
        for (int i = 0; i < DIR[piece.getValue()].length; i++) {
            // BLACK은 x축에 -1을 곱해서 이동
            int x = cur.x + (playerType == PlayerType.BLACK ? -1 : 1) * DIR[piece.getValue()][i][0];
            int y = cur.y + DIR[piece.getValue()][i][1];

            if (x >= 0 && x < 4 && y >= 0 && y < 3 && board[x][y].getPlayerType() != playerType) {
                board[x][y].setHighlighted(true);
                highlightedList.add(new Pos(x, y));
            }
        }

        if (highlightedList.size() == 0) {
            // 해당 기물이 이동할 수 있는 공간이 없는 경우
            throw new CouldNotMoveException();
        }

        // 이동 가능한 공간이 있을 경우 포인트, 이동 가능한 공간 리스트 반환
        board[cur.x][cur.y].setPointed(true);
        GameRepository.getInstance().setGame(game);

        return highlightedList;
    }

    public List<Pos> highlight(String gameId, PlayerType playerType, PieceType pieceType)
            throws DoNotHavePieceException, CouldNotSummonException, NotYourTurnException, GameNotFoundException {
        Game game = GameRepository.getInstance().getGame(gameId);
        checkError(game, playerType);

        Piece[][] board = game.getBoard();
        List<Piece> pieceList = (playerType == PlayerType.WHITE ? game.getWhitePieces() : game.getBlackPieces());

        resetBagPoint(pieceList);
        resetBoardPoint(board);

        List<Pos> highlightedList = new ArrayList<>();

        // 해당 플레이어의 보유중인 기물 리스트 & 플레이어 타입에 따른 범위 지정
        int s = playerType.getValue() - 1, e = playerType.getValue() + 1;

        boolean state = false;

        for (Piece piece : pieceList) {
            if (piece.getPieceType() == pieceType) {
                state = true;
                // 포인트 초기화 & 재지정
                resetBagPoint(pieceList);
                resetBoardPoint(board);
                piece.setPointed(true);
                for (int i = s; i <= e; i++) {
                    for (int j = 0; j < 3; j++) {
                        // 범위 내 빈 공간 리스트
                        if (board[i][j].getPieceType() == PieceType.BLANK) {
                            board[i][j].setHighlighted(true);
                            highlightedList.add(new Pos(i, j));
                        }
                    }
                }
            }
        }

        if (!state) {
            // 해당 기물을 보유중이지 않을 경우
            throw new DoNotHavePieceException();
        } else if (highlightedList.size() == 0) {
            // 해당 말이 이동할 수 있는 공간이 없는 경우
            resetBagPoint(pieceList);
            throw new CouldNotSummonException();
        }

        GameRepository.getInstance().setGame(game);

        return highlightedList;
    }

    public Game move(String gameId, PlayerType playerType, Pos next)
            throws CouldNotMoveException, NoPointedPieceException, NotYourTurnException, GameNotFoundException {
        Game game = GameRepository.getInstance().getGame(gameId);
        checkError(game, playerType);

        Piece[][] board = game.getBoard();

        // 해당 좌표가 이동할 수 없는 공간인 경우
        if (!board[next.x][next.y].isHighlighted()) {
            // 포인트 초기화
            resetBoardPoint(board);
            throw new CouldNotMoveException();
        } else {
            Pos pre = null;
            boolean state = false;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j].isPointed()) {
                        pre = new Pos(i, j);
                        state = true;
                        break;
                    }
                }
                if (state) break;
            }

            // 포인트 된 기물이 보드에 없을 경우
            if (!state) throw new NoPointedPieceException();

            List<Piece> pieceList = (playerType == PlayerType.WHITE ? game.getWhitePieces() : game.getBlackPieces());

            // 상대편 말을 잡았을 경우 자신의 수중에 넣음
            if (board[next.x][next.y].getPieceType() != PieceType.BLANK) {
                board[next.x][next.y].setPlayerType(playerType);
                board[next.x][next.y].setHighlighted(false);
                pieceList.add(board[next.x][next.y]);
            }

            // 이전 좌표, 새 좌표 수정
            board[next.x][next.y] = board[pre.x][pre.y];
            board[pre.x][pre.y] = new Piece(PlayerType.NONE, PieceType.BLANK);

            // 명령 저장
            Order order = new Order(playerType, board[next.x][next.y].getPieceType(),
                    new Pos(pre.x, pre.y), new Pos(next.x, next.y));
            log.info("Move : {}", order);
            game.getOrderDtoList().add(order);

            // JA가 상대편 진영 맨 끝에 도달했을 경우 HU로 승격
            if ((next.x == 0 || next.x == 3) && board[next.x][next.y].getPieceType() == PieceType.JA) {
                board[next.x][next.y].setPieceType(PieceType.HU);
            }

            // 포인트 초기화, 턴 넘김, 게임 종료 여부 확인, 변경된 게임 정보 저장 후 반환
            resetBoardPoint(board);
            endTurn(game, playerType);

            return game;
        }
    }

    public Game summon(String gameId, PlayerType playerType, Pos cur)
            throws CouldNotSummonException, NoPointedPieceException, NotYourTurnException, GameNotFoundException {
        Game game = GameRepository.getInstance().getGame(gameId);
        checkError(game, playerType);

        Piece[][] board = game.getBoard();
        List<Piece> pieceList = (playerType == PlayerType.WHITE ? game.getWhitePieces() : game.getBlackPieces());

        // 해당 좌표가 소환할 수 없는 공간인 경우
        if (!board[cur.x][cur.y].isHighlighted()) {
            // 포인트 초기화
            resetBagPoint(pieceList);
            resetBoardPoint(board);
            throw new CouldNotSummonException();
        } else {
            Piece piece = null;
            boolean state = false;
            for (Piece p : pieceList) {
                if (p.isPointed()) {
                    state = true;
                    piece = p;
                    break;
                }
            }

            // 포인트된 기물이 수중에 없을 경우
            if (!state) throw new NoPointedPieceException();

            // 기물 보드에 이동 후 리스트에서 삭제
            board[cur.x][cur.y] = piece;
            pieceList.remove(piece);

            // 명령 저장
            Order order = new Order(playerType, piece.getPieceType(), new Pos(-1, -1), new Pos(cur.x, cur.y));
            log.info("Summon : {}", order);
            game.getOrderDtoList().add(order);

            // 포인트 초기화, 턴 넘김, 게임 종료 여부 확인, 변경된 게임 정보 저장 후 반환
            resetBoardPoint(board);
            endTurn(game, playerType);

            return game;
        }
    }

    public void checkError(Game game, PlayerType playerType) throws NotYourTurnException, GameNotFoundException {
        if (game == null) {
            // 게임이 존재하지 않을 경우
            throw new GameNotFoundException();
        } else if (playerType != game.getTurn()) {
            // 플레이어의 차례가 아닐 경우
            throw new NotYourTurnException();
        }
    }

    // 턴 종료
    public void endTurn(Game game, PlayerType playerType) {
        game.setWinner(checkGame(game));

        if (game.getWinner() != PlayerType.NONE) {
            // 게임이 종료되었을 시 게임 상태 변경 후 db에 저장, 인스턴스에서 삭제
            log.info("Game Finished : {}", game.getWinner());
            game.setGameStatus(GameStatus.FINISHED);
            gameRecordService.saveGame(game);
            GameRepository.getInstance().removeGame(game.getGameId());
        } else {
            // 종료되지 않았을 시 턴 교체 후 인스턴스 저장
            game.setTurn(playerType == PlayerType.WHITE ? PlayerType.BLACK : PlayerType.WHITE);
            GameRepository.getInstance().setGame(game);
        }
    }

    PlayerType checkGame(Game game) {
        Piece[][] board = game.getBoard();
        boolean whiteWangExisted = false, blackWangExisted = false;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getPieceType() == PieceType.WANG) {
                    // WHITE WANG, BLACK WANG 유무 확인
                    if (board[i][j].getPlayerType() == PlayerType.WHITE) {
                        whiteWangExisted = true;

                        // 상대 진영에서 한 턴 버텼을 경우 게임 종료
                        if (i == 3) {
                            if (game.getState() == PlayerType.WHITE) {
                                return PlayerType.WHITE;
                            }
                            game.setState(PlayerType.WHITE);
                        }
                    } else {
                        blackWangExisted = true;

                        // 상대 진영에서 한 턴 버텼을 경우 게임 종료
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
            // WHITE WANG 없음
            return PlayerType.BLACK;
        } else if (!blackWangExisted) {
            // BLACK WANG 없음
            return PlayerType.WHITE;
        }

        // 게임 속행
        return PlayerType.NONE;
    }

    // 가방 초기화
    public void resetBagPoint(List<Piece> list) {
        for (Piece piece : list) {
            piece.setPointed(false);
            piece.setHighlighted(false);
        }
    }

    // 보드 초기화
    public void resetBoardPoint(Piece[][] board) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j].setPointed(false);
                board[i][j].setHighlighted(false);
            }
        }
    }

}
