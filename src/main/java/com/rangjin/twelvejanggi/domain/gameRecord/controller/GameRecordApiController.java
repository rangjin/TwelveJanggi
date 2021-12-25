package com.rangjin.twelvejanggi.domain.gameRecord.controller;

import com.rangjin.twelvejanggi.domain.gameRecord.service.GameRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/gameRecord")
public class GameRecordApiController {

    private GameRecordService gameRecordService;

    @GetMapping("/{gameId}")
    public ResponseEntity<?> getGameData(@PathVariable("gameId") String gameId) {
        return new ResponseEntity<>(gameRecordService.findByGameId(gameId), HttpStatus.OK);
    }

}
