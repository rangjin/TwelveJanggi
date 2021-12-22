package com.rangjin.twelvejanggi.controller;

import com.rangjin.twelvejanggi.exception.*;
import com.rangjin.twelvejanggi.model.order.MoveOrder;
import com.rangjin.twelvejanggi.model.order.SummonOrder;
import com.rangjin.twelvejanggi.model.player.Player;
import com.rangjin.twelvejanggi.service.GameService;
import com.rangjin.twelvejanggi.service.TwelveJanggiService;
import com.rangjin.twelvejanggi.service.dto.ConnectRequest;
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
    public ResponseEntity<?> connectGame(@RequestBody ConnectRequest connectRequest) throws GameNotFoundException, GameAlreadyStartedException {
        log.info("Connect Game : {}", connectRequest);
        return new ResponseEntity<>(gameService.connect(connectRequest.getPlayer(), connectRequest.getGameId()), HttpStatus.OK);
    }

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

}
