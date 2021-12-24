package com.rangjin.twelvejanggi.service;

import com.rangjin.twelvejanggi.exception.*;
import com.rangjin.twelvejanggi.model.Order;
import com.rangjin.twelvejanggi.model.Pos;
import com.rangjin.twelvejanggi.model.game.Game;
import com.rangjin.twelvejanggi.model.game.GameStatus;
import com.rangjin.twelvejanggi.model.piece.Piece;
import com.rangjin.twelvejanggi.model.piece.PieceType;
import com.rangjin.twelvejanggi.model.player.PlayerType;
import com.rangjin.twelvejanggi.service.dto.OrderResponseDto;
import com.rangjin.twelvejanggi.storage.GameStorage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class GameService {

    private final GameRecordService gameRecordService;

    static final int[][][] DIR = {
            {},
            {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0,   1}, {-1, -1}, {-1, 0}, {-1, -1}},
            {{1, -1}, {1, 1}, {-1, -1}, {-1, 1}},
            {{1, 0}, {0, -1}, {0, 1}, {-1, 0}},
            {{1, 0}},
            {{1, -1}, {1, 0}, {1, 1}, {0, -1}, {0, 1}, {-1, 0}}
    };

    public OrderResponseDto<?> select(String gameId, PlayerType playerType, Pos cur)
            throws NotYourTurnException, NotYourPieceException, CouldNotMoveException, CouldNotSummonException {
        Game game = GameStorage.getInstance().getGame(gameId);
        Piece[][] board = game.getBoard();

        if (playerType != game.getTurn()) {
            // 플레이어의 차례가 아닐 경우
            throw new NotYourTurnException();
        }

        List<Piece> list;

        // 보유중인 기물 리스트
        if (playerType == PlayerType.WHITE) {
            list = game.getWhitePieces();
        } else {
            list = game.getBlackPieces();
        }

        // 보드에 포인트된 기물이 있는지 확인
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].isPointed()) {
                    // 있을경우 이동
                    return move(game, playerType, list, new Pos(i, j), cur);
                }
            }
        }

        // 보유 중인 포인트된 기물이 있는지 확인
        for (Piece piece : list) {
            if (piece.isPointed()) {
                // 있을 경우 소환
                return summon(game, playerType, list, piece, cur);
            }
        }

        // 포인트된 기물이 없음 -> 첫 번째 선택
        if (playerType != board[cur.x][cur.y].getPlayerType()) {
            // 첫 번째로 선택하는 기물이 플레이어의 기물이 아닐 경우
            throw new NotYourPieceException();
        }

        // 첫 번째로 선택하는 기물이 자신의 기물일 경우 경로 체크
        return highlight(board, playerType, cur);
    }

    public OrderResponseDto<?> select(String gameId, PlayerType playerType, PieceType pieceType)
            throws NotYourTurnException, CouldNotSummonException, DoNotHavePieceException {
        Game game = GameStorage.getInstance().getGame(gameId);
        Piece[][] board = game.getBoard();

        if (playerType != game.getTurn()) {
            // 플레이어의 차례가 아닐 경우
            throw new NotYourTurnException();
        }

        List<Piece> pieceList;
        List<Pos> highlightedList = new ArrayList<>();

        int s, e;

        // 해당 플레이어의 보유중인 기물 리스트 & 플레이어 타입에 따른 범위 지정
        if (playerType == PlayerType.WHITE) {
            pieceList = game.getWhitePieces();
            s = 0;
            e = 2;
        } else {
            pieceList = game.getBlackPieces();
            s = 1;
            e = 3;
        }

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
            throw new CouldNotSummonException();
        }

        // 이동 가능한 공간 리스트 반환
        return new OrderResponseDto<>(playerType, highlightedList);
    }

    public OrderResponseDto<List<Pos>> highlight(Piece[][] board, PlayerType playerType, Pos cur)
            throws CouldNotMoveException {
        ArrayList<Pos> highlightedList = new ArrayList<>();
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
        return new OrderResponseDto<>(playerType, highlightedList);
    }

    public OrderResponseDto<?> move(Game game, PlayerType playerType, List<Piece> list, Pos pre, Pos next)
            throws CouldNotMoveException {
        Piece[][] board = game.getBoard();

        // 해당 좌표가 이동할 수 없는 공간인 경우
        if (!board[next.x][next.y].isHighlighted()) {
            // 포인트 초기화
            resetBagPoint(list);
            resetBoardPoint(board);
            if (board[next.x][next.y].getPlayerType() == playerType) {
                // 해당 플레이어의 기물일 경우 포인트 새로 지정
                return highlight(board, playerType, next);
            } else {
                // 해당 플레이어의 기물이 아닐 경우 이동 불가능
                throw new CouldNotMoveException();
            }
        } else {
            // 상대편 말을 잡았을 경우 자신의 수중에 넣음
            if (board[next.x][next.y].getPieceType() != PieceType.BLANK) {
                board[next.x][next.y].setPlayerType(playerType);
                board[next.x][next.y].setHighlighted(false);
                list.add(board[next.x][next.y]);
            }

            // 이전 좌표, 새 좌표 수정
            board[next.x][next.y] = board[pre.x][pre.y];
            board[pre.x][pre.y] = new Piece(PlayerType.NONE, PieceType.BLANK);

            // 명령 저장
            game.getOrderDtoList().add(
                    new Order(playerType, board[next.x][next.y].getPieceType(),
                            new Pos(pre.x, pre.y), new Pos(next.x, next.y)));

            // JA가 상대편 진영 맨 끝에 도달했을 경우 HU로 승격
            if ((next.x == 0 || next.x == 3) && board[next.x][next.y].getPieceType() == PieceType.JA) {
                board[next.x][next.y].setPieceType(PieceType.HU);
            }

            // 포인트 초기화, 턴 넘김, 게임 종료 여부 확인, 변경된 게임 정보 저장 후 반환
            resetBoardPoint(board);
            endTurn(game, playerType);
            return new OrderResponseDto<>(playerType, game);
        }
    }

    public OrderResponseDto<?> summon(Game game, PlayerType playerType, List<Piece> list, Piece piece, Pos cur)
            throws CouldNotMoveException, CouldNotSummonException {
        Piece[][] board = game.getBoard();

        // 해당 좌표가 소환할 수 없는 공간인 경우
        if (!board[cur.x][cur.y].isHighlighted()) {
            // 포인트 초기화
            resetBagPoint(list);
            resetBoardPoint(board);
            if (board[cur.x][cur.y].getPlayerType() == playerType) {
                // 해당 플레이어의 기물일 경우 포인트 새로 지정
                return highlight(board, playerType, cur);
            } else {
                // 소환 불가능한 공간
                throw new CouldNotSummonException();
            }
        } else {
            // 기물 보드에 이동 후 리스트에서 삭제
            board[cur.x][cur.y] = piece;
            list.remove(piece);

            // 명령 저장
            game.getOrderDtoList().add(
                    new Order(playerType, piece.getPieceType(), new Pos(-1, -1), new Pos(cur.x, cur.y)));

            // 포인트 초기화, 턴 넘김, 게임 종료 여부 확인, 변경된 게임 정보 저장 후 반환
            resetBoardPoint(board);
            endTurn(game, playerType);
            return new OrderResponseDto<>(playerType, game);
        }
    }

    // 턴 종료
    public void endTurn(Game game, PlayerType playerType) {
        game.setWinner(checkGame(game));

        if (game.getWinner() != PlayerType.NONE) {
            // 게임이 종료되었을 시 게임 상태 변경 후 db에 저장, 인스턴스에서 삭제
            game.setGameStatus(GameStatus.FINISHED);
            gameRecordService.saveGame(game);
            GameStorage.getInstance().removeGame(game.getGameId());
        } else {
            // 종료되지 않았을 시 턴 교체 후 인스턴스 저장
            game.setTurn(playerType == PlayerType.WHITE ? PlayerType.BLACK : PlayerType.WHITE);
            GameStorage.getInstance().setGame(game);
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
