package com.rangjin.twelvejanggi.service;

import com.rangjin.twelvejanggi.domain.GameRecord;
import com.rangjin.twelvejanggi.domain.OrderRecord;
import com.rangjin.twelvejanggi.model.Order;
import com.rangjin.twelvejanggi.model.game.Game;
import com.rangjin.twelvejanggi.repository.GameRecordRepository;
import com.rangjin.twelvejanggi.repository.OrderRepository;
import com.rangjin.twelvejanggi.service.dto.GameRecordDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameRecordService {

    private final GameRecordRepository gameRecordRepository;
    private final OrderRepository orderRepository;

    public void saveGame(Game game) {
        GameRecord gameRecord = new GameRecord(game);
        gameRecordRepository.save(gameRecord);
        List<Order> list = game.getOrderDtoList();
        for (Order dto : list) {
            OrderRecord orderRecord = new OrderRecord(dto, gameRecord);
            orderRepository.save(orderRecord);
        }
    }

    public GameRecordDto findByGameId(String gameId) {
        return new GameRecordDto(gameRecordRepository.findByGameId(gameId));
    }

}
