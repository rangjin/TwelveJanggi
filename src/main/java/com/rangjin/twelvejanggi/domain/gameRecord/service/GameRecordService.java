package com.rangjin.twelvejanggi.domain.gameRecord.service;

import com.rangjin.twelvejanggi.domain.gameRecord.entity.GameRecord;
import com.rangjin.twelvejanggi.domain.game.model.game.Game;
import com.rangjin.twelvejanggi.domain.gameRecord.repository.GameRecordRepository;
import com.rangjin.twelvejanggi.domain.user.entity.User;
import com.rangjin.twelvejanggi.domain.user.repository.UserRepository;
import com.rangjin.twelvejanggi.domain.gameRecord.controller.response.GameRecordResponseDto;
import com.rangjin.twelvejanggi.domain.orderRecord.service.OrderRecordService;
import com.rangjin.twelvejanggi.global.exception.PlayerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameRecordService {

    private final UserRepository userRepository;
    private final GameRecordRepository gameRecordRepository;
    private final OrderRecordService orderRecordService;

    public void saveGame(Game game) {
        // 종료된 게임 저장
        User white = userRepository.findByName(game.getWhite().getName())
                .orElseThrow(PlayerNotFoundException::new);
        User black = userRepository.findByName(game.getBlack().getName())
                .orElseThrow(PlayerNotFoundException::new);

        GameRecord gameRecord = new GameRecord(game, white, black);
        gameRecordRepository.save(gameRecord);

        // 게임 명령 목록 저장
        orderRecordService.saveOrderList(game.getOrderDtoList(), gameRecord);

        log.info("Save Game Data : {}", game);
    }

    public GameRecordResponseDto findByGameId(String gameId) {
        return new GameRecordResponseDto(gameRecordRepository.findByGameId(gameId));
    }

}
