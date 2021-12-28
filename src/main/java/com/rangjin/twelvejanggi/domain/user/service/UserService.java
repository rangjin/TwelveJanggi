package com.rangjin.twelvejanggi.domain.user.service;

import com.rangjin.twelvejanggi.domain.user.entity.User;
import com.rangjin.twelvejanggi.domain.user.repository.UserRepository;
import com.rangjin.twelvejanggi.domain.user.controller.request.RegisterRequestDto;
import com.rangjin.twelvejanggi.domain.user.controller.response.UserResponseDto;
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
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserResponseDto create(RegisterRequestDto dto) {
        User player = User.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        return new UserResponseDto(userRepository.save(player));
    }

    public UserResponseDto findByUsername(String username) {
        return new UserResponseDto(userRepository.findByUsername(username)
                .orElseThrow(PlayerNotFoundException::new));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(PlayerNotFoundException::new);
    }

}
