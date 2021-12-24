package com.rangjin.twelvejanggi.controller;

import com.rangjin.twelvejanggi.game.exception.*;
import com.rangjin.twelvejanggi.game.model.Pos;
import com.rangjin.twelvejanggi.game.model.piece.PieceType;
import com.rangjin.twelvejanggi.game.model.player.Player;
import com.rangjin.twelvejanggi.service.GameRecordService;
import com.rangjin.twelvejanggi.game.service.ConnectService;
import com.rangjin.twelvejanggi.game.service.GameService;
import com.rangjin.twelvejanggi.controller.dto.request.ConnectRequestDto;
import com.rangjin.twelvejanggi.controller.dto.request.OrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/game")
public class GameApiController {

    private final ConnectService gameService;
    private final GameService twelveJanggiService;
    private final GameRecordService gameRecordService;

    @PostMapping("/create")
    public ResponseEntity<?> createGame(@RequestBody Player player) {
        log.info("Start Game : {}", player);
        return new ResponseEntity<>(gameService.create(player), HttpStatus.OK);
    }

    @PostMapping("/connect")
    public ResponseEntity<?> connectGame(@RequestBody ConnectRequestDto<Player> dto)
            throws GameNotFoundException, GameAlreadyStartedException {
        log.info("Connect Game : {}", dto);
        return new ResponseEntity<>(gameService.connect(dto.getPlayer(), dto.getGameId()), HttpStatus.OK);
    }

    @PostMapping("/selectInBoard")
    public ResponseEntity<?> selectInBoard(@RequestBody OrderRequestDto<Pos> dto)
            throws NotYourTurnException, NotYourPieceException, CouldNotMoveException, CouldNotSummonException, GameNotFoundException {
        return new ResponseEntity<>(twelveJanggiService.select(dto.getGameId(), dto.getPlayerType(), dto.getData()), HttpStatus.OK);
    }

    @PostMapping("/selectInBag")
    public ResponseEntity<?> selectInBag(@RequestBody OrderRequestDto<PieceType> dto)
            throws NotYourTurnException, CouldNotSummonException, DoNotHavePieceException, GameNotFoundException {
        return new ResponseEntity<>(twelveJanggiService.select(dto.getGameId(), dto.getPlayerType(), dto.getData()), HttpStatus.OK);
    }

    @GetMapping("/getGameData/{gameId}")
    public ResponseEntity<?> getGameData(@PathVariable("gameId") String gameId) {
        return new ResponseEntity<>(gameRecordService.findByGameId(gameId), HttpStatus.OK);
    }

}
