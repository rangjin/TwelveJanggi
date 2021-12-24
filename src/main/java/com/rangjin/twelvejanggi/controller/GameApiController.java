package com.rangjin.twelvejanggi.controller;

import com.rangjin.twelvejanggi.exception.*;
import com.rangjin.twelvejanggi.model.Pos;
import com.rangjin.twelvejanggi.model.piece.PieceType;
import com.rangjin.twelvejanggi.model.player.Player;
import com.rangjin.twelvejanggi.service.GameRecordService;
import com.rangjin.twelvejanggi.service.ConnectService;
import com.rangjin.twelvejanggi.service.GameService;
import com.rangjin.twelvejanggi.service.dto.ConnectRequestDto;
import com.rangjin.twelvejanggi.service.dto.OrderRequestDto;
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
    public ResponseEntity<?> connectGame(@RequestBody ConnectRequestDto<Player> dto) throws GameNotFoundException, GameAlreadyStartedException {
        log.info("Connect Game : {}", dto);
        return new ResponseEntity<>(gameService.connect(dto.getPlayer(), dto.getGameId()), HttpStatus.OK);
    }

    @PostMapping("/selectInBoard")
    public ResponseEntity<?> selectInBoard(@RequestBody OrderRequestDto<Pos> dto) throws NotYourTurnException, NotYourPieceException, CouldNotMoveException, CouldNotSummonException {
        return new ResponseEntity<>(twelveJanggiService.select(dto.getGameId(), dto.getPlayerType(), dto.getData()), HttpStatus.OK);
    }

    @PostMapping("/selectInBag")
    public ResponseEntity<?> selectInBag(@RequestBody OrderRequestDto<PieceType> dto) throws NotYourTurnException, CouldNotSummonException, DoNotHavePieceException {
        return new ResponseEntity<>(twelveJanggiService.select(dto.getGameId(), dto.getPlayerType(), dto.getData()), HttpStatus.OK);
    }

    @GetMapping("/getGameData/{gameId}")
    public ResponseEntity<?> getGameData(@PathVariable("gameId") String gameId) {
        return new ResponseEntity<>(gameRecordService.findByGameId(gameId), HttpStatus.OK);
    }

}
