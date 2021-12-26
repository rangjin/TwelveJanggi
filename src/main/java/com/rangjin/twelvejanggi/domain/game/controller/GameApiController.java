package com.rangjin.twelvejanggi.domain.game.controller;

import com.rangjin.twelvejanggi.domain.game.controller.response.OrderResponseDto;
import com.rangjin.twelvejanggi.domain.game.model.Pos;
import com.rangjin.twelvejanggi.domain.game.model.piece.PieceType;
import com.rangjin.twelvejanggi.domain.game.model.player.Player;
import com.rangjin.twelvejanggi.domain.game.service.GameService;
import com.rangjin.twelvejanggi.domain.game.controller.request.ConnectRequestDto;
import com.rangjin.twelvejanggi.domain.game.controller.request.OrderRequestDto;
import com.rangjin.twelvejanggi.global.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameApiController {

    private final GameService gameService;

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
    public ResponseEntity<?> selectInBoard(@RequestBody OrderRequestDto<Pos> dto) throws NotYourTurnException, GameNotFoundException, CouldNotMoveException, NotYourPieceException {
        return new ResponseEntity<>(new OrderResponseDto<>(gameService.highlight(dto.getGameId(), dto.getPlayerType(), dto.getData())), HttpStatus.OK);
    }

    @PostMapping("/selectInBag")
    public ResponseEntity<?> selectInBag(@RequestBody OrderRequestDto<PieceType> dto) throws NotYourTurnException, GameNotFoundException, CouldNotSummonException, DoNotHavePieceException {
        return new ResponseEntity<>(new OrderResponseDto<>(gameService.highlight(dto.getGameId(), dto.getPlayerType(), dto.getData())), HttpStatus.OK);
    }

    @PostMapping("/move")
    public ResponseEntity<?> move(@RequestBody OrderRequestDto<Pos> dto) throws NoPointedPieceException, CouldNotMoveException, NotYourTurnException, GameNotFoundException {
        return new ResponseEntity<>(gameService.move(dto.getGameId(), dto.getPlayerType(), dto.getData()), HttpStatus.OK);
    }

    @PostMapping("/summon")
    public ResponseEntity<?> summon(@RequestBody OrderRequestDto<Pos> dto) throws NoPointedPieceException, NotYourTurnException, GameNotFoundException, CouldNotSummonException {
        return new ResponseEntity<>(gameService.summon(dto.getGameId(), dto.getPlayerType(), dto.getData()), HttpStatus.OK);
    }

}
