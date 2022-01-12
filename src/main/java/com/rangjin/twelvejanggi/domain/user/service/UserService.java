package com.rangjin.twelvejanggi.domain.user.service;

import com.rangjin.twelvejanggi.domain.user.entity.User;
import com.rangjin.twelvejanggi.domain.user.repository.UserRepository;
import com.rangjin.twelvejanggi.domain.user.controller.response.UserDto;
import com.rangjin.twelvejanggi.global.exception.PlayerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long saveOrUpdate(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            user.update(userDto);
        } else {
            user = User.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .picture(userDto.getPicture())
                    .role(userDto.getRole())
                    .refreshToken(userDto.getRefreshToken())
                    .build();

        }

        return userRepository.save(user).getId();
    }

    public void setRefreshToken(Long id, String refreshToken) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        } else {
            throw new PlayerNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    public User loadUser(Long id) {
        return userRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
    }

}
