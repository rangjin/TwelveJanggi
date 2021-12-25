package com.rangjin.twelvejanggi.domain.gameRecord.service;

import com.rangjin.twelvejanggi.domain.gameRecord.entity.GameRecord;
import com.rangjin.twelvejanggi.domain.game.model.game.Game;
import com.rangjin.twelvejanggi.domain.gameRecord.repository.GameRecordRepository;
import com.rangjin.twelvejanggi.global.dto.GameRecordDto;
import com.rangjin.twelvejanggi.domain.orderRecord.service.OrderRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GameRecordService {

    private final GameRecordRepository gameRecordRepository;
    private final OrderRecordService orderRecordService;

    public void saveGame(Game game) {
        // 종료된 게임 저장
        log.info("Save Game Data : {}", game);
        GameRecord gameRecord = new GameRecord(game);
        gameRecordRepository.save(gameRecord);

        // 게임 명령 목록 저장
        orderRecordService.saveOrderList(game.getOrderDtoList(), gameRecord);
    }

    public GameRecordDto findByGameId(String gameId) {
        return new GameRecordDto(gameRecordRepository.findByGameId(gameId));
    }

}
