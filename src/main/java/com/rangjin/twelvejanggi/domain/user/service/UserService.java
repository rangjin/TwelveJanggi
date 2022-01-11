package com.rangjin.twelvejanggi.domain.user.service;

import com.rangjin.twelvejanggi.domain.user.entity.User;
import com.rangjin.twelvejanggi.domain.user.repository.UserRepository;
import com.rangjin.twelvejanggi.domain.user.controller.response.UserDto;
import com.rangjin.twelvejanggi.global.exception.PlayerNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveOrUpdate(UserDto userDto) {
        Optional<User> exist = userRepository.findByEmail(userDto.getEmail());
        User user;
        if (exist.isPresent()) {
            user = exist.get();
            user.update(userDto);
        } else {
            user = User.builder()
                    .email(userDto.getEmail())
                    .name(userDto.getName())
                    .picture(userDto.getPicture())
                    // todo: [] 제거 필요
                    .role(userDto.getRole())
                    .build();

        }
        userRepository.save(user);
    }

    public UserDto findByEmail(String email) {
        return new UserDto(userRepository.findByEmail(email).orElseThrow(PlayerNotFoundException::new));
    }

}
