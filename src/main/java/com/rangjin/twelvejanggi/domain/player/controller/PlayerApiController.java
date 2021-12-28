package com.rangjin.twelvejanggi.domain.player.controller;

import com.rangjin.twelvejanggi.domain.player.controller.request.LoginRequestDto;
import com.rangjin.twelvejanggi.domain.player.controller.response.PlayerResponseDto;
import com.rangjin.twelvejanggi.domain.player.service.PlayerService;
import com.rangjin.twelvejanggi.domain.player.controller.request.RegisterRequestDto;
import com.rangjin.twelvejanggi.global.dto.FormResponse;
import com.rangjin.twelvejanggi.global.security.provider.JwtTokenProvider;
import com.rangjin.twelvejanggi.global.validation.LoginPlayerValidator;
import com.rangjin.twelvejanggi.global.validation.RegisterPlayerValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerApiController {

    private final PlayerService playerService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterRequestDto dto, Errors errors) {
        new RegisterPlayerValidator(playerService).validate(dto, errors);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        return new ResponseEntity<>(new FormResponse<>(true, playerService.create(dto)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto, Errors errors) {
        new LoginPlayerValidator(playerService).validate(dto, errors);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        PlayerResponseDto responseDto = playerService.findByUsername(dto.getUsername());

        return new ResponseEntity<>(new FormResponse<>(true,
                jwtTokenProvider.createToken(responseDto.getUsername())), HttpStatus.OK);
    }

}
