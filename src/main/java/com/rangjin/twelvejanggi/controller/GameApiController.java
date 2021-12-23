package com.rangjin.twelvejanggi.controller;

import com.rangjin.twelvejanggi.exception.*;
import com.rangjin.twelvejanggi.model.Pos;
import com.rangjin.twelvejanggi.model.piece.PieceType;
import com.rangjin.twelvejanggi.model.player.Player;
import com.rangjin.twelvejanggi.service.GameService;
import com.rangjin.twelvejanggi.service.TwelveJanggiService;
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

    private final GameService gameService;
    private final TwelveJanggiService twelveJanggiService;

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

    /*
    @PostMapping("/move")
    public ResponseEntity<?> moveOrder(@RequestBody MoveOrder order) throws GameNotFoundException, NotYourTurnException, CouldNotMoveException {
        log.info("Move Piece : {}", order.getGameId() + ", " + order);
        return new ResponseEntity<>(twelveJanggiService.move(order), HttpStatus.OK);
    }

    @PostMapping("/summon")
    public ResponseEntity<?> summonOrder(@RequestBody SummonOrder order) throws GameNotFoundException, NotYourTurnException, CouldNotSummonException {
        log.info("Summon Piece : {}", order.getGameId() + ", " + order);
        return new ResponseEntity<>(twelveJanggiService.summon(order), HttpStatus.OK);
    }

     */

}
