package com.rangjin.twelvejanggi.domain.orderRecord.service;

import com.rangjin.twelvejanggi.domain.gameRecord.entity.GameRecord;
import com.rangjin.twelvejanggi.domain.orderRecord.entity.OrderRecord;
import com.rangjin.twelvejanggi.domain.game.model.Order;
import com.rangjin.twelvejanggi.domain.orderRecord.repository.OrderRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderRecordService {

    private final OrderRecordRepository orderRecordRepository;

    public void saveOrderList(List<Order> list, GameRecord gameRecord) {
        for (Order dto : list) {
            OrderRecord orderRecord = new OrderRecord(dto, gameRecord);
            orderRecordRepository.save(orderRecord);
        }
    }

}
