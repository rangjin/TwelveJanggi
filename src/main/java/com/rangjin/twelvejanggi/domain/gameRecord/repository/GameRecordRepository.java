package com.rangjin.twelvejanggi.domain.gameRecord.repository;

import com.rangjin.twelvejanggi.domain.gameRecord.entity.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

    GameRecord findByGameId(String gameId);

}
