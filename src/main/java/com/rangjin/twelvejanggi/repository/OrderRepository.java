package com.rangjin.twelvejanggi.repository;

import com.rangjin.twelvejanggi.domain.OrderRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderRecord, Long> {

}
