package com.rangjin.twelvejanggi.domain.player.service;

import com.rangjin.twelvejanggi.domain.player.entity.PlayerEntity;
import com.rangjin.twelvejanggi.domain.player.repository.PlayerEntityRepository;
import com.rangjin.twelvejanggi.domain.player.controller.request.RegisterRequestDto;
import com.rangjin.twelvejanggi.domain.player.controller.response.PlayerResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerService {

    private final PlayerEntityRepository playerEntityRepository;

    public PlayerResponseDto create(RegisterRequestDto dto) {
        PlayerEntity player = PlayerEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        return new PlayerResponseDto(playerEntityRepository.save(player));
    }

    public boolean existsByUsername(String username) {
        return playerEntityRepository.existsByUsername(username);
    }

}
