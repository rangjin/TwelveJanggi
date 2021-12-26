package com.rangjin.twelvejanggi.domain.player.repository;

import com.rangjin.twelvejanggi.domain.player.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerEntityRepository extends JpaRepository<PlayerEntity, Long> {

    Optional<PlayerEntity> findByUsername(String username);

    boolean existsByUsername(String username);

}
