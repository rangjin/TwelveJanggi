package com.rangjin.twelvejanggi.global.validation;

import com.rangjin.twelvejanggi.domain.player.controller.request.RegisterRequestDto;
import com.rangjin.twelvejanggi.domain.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class RegisterPlayerValidator implements Validator {

    private final PlayerService playerService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequestDto dto = (RegisterRequestDto) target;

        if (playerService.existsByUsername(dto.getUsername())) {
            errors.rejectValue("username", "overlap", "사용할 수 없는 아이디입니다");
        }

        if (!dto.getPassword().equals(dto.getRePassword())) {
            errors.rejectValue("password", "unMatched", "비밀번호가 일치하지 않습니다");
        }

    }

}
