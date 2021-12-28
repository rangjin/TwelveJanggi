package com.rangjin.twelvejanggi.global.validation;

import com.rangjin.twelvejanggi.domain.player.controller.request.LoginRequestDto;
import com.rangjin.twelvejanggi.domain.player.service.PlayerEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
public class LoginPlayerValidator implements Validator {

    private final PlayerEntityService playerEntityService;

    @Override
    public boolean supports(Class<?> clazz) {
        return LoginRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LoginRequestDto dto = (LoginRequestDto) target;

        if (!playerEntityService.existsByUsername(dto.getUsername())) {
            errors.rejectValue("username", "noExists", "해당 아이디가 존재하지 않습니다");
        }
    }

}
