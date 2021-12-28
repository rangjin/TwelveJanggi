package com.rangjin.twelvejanggi.domain.player.service;

import com.rangjin.twelvejanggi.domain.player.entity.PlayerEntity;
import com.rangjin.twelvejanggi.domain.player.repository.PlayerEntityRepository;
import com.rangjin.twelvejanggi.domain.player.controller.request.RegisterRequestDto;
import com.rangjin.twelvejanggi.domain.player.controller.response.PlayerEntityResponseDto;
import com.rangjin.twelvejanggi.global.exception.PlayerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PlayerEntityService implements UserDetailsService {

    private final PlayerEntityRepository playerEntityRepository;

    public PlayerEntityResponseDto create(RegisterRequestDto dto) {
        PlayerEntity player = PlayerEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        return new PlayerEntityResponseDto(playerEntityRepository.save(player));
    }

    public PlayerEntityResponseDto findByUsername(String username) {
        return new PlayerEntityResponseDto(playerEntityRepository.findByUsername(username)
                .orElseThrow(PlayerNotFoundException::new));
    }

    public boolean existsByUsername(String username) {
        return playerEntityRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerEntityRepository.findByUsername(username).orElseThrow(PlayerNotFoundException::new);
    }

}
