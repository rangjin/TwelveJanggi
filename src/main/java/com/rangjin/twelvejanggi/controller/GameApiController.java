package com.rangjin.twelvejanggi.controller;

import com.rangjin.twelvejanggi.exception.GameAlreadyStartedException;
import com.rangjin.twelvejanggi.exception.GameNotFoundException;
import com.rangjin.twelvejanggi.model.Player;
import com.rangjin.twelvejanggi.service.GameService;
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

}
