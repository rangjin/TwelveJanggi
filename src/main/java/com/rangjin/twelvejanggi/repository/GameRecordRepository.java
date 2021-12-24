package com.rangjin.twelvejanggi.repository;

import com.rangjin.twelvejanggi.domain.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

    GameRecord findByGameId(String gameId);

}
