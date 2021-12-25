package com.rangjin.twelvejanggi.domain.orderRecord.repository;

import com.rangjin.twelvejanggi.domain.orderRecord.entity.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRecordRepository extends JpaRepository<OrderRecord, Long> {

}
