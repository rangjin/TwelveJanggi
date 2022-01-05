package com.rangjin.twelvejanggi.domain.user.controller;

import com.rangjin.twelvejanggi.domain.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/social")
public class SocialApiController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao/token")
    public ResponseEntity<?> kakaoToken(@RequestParam("code") String code) {
        return new ResponseEntity<>(kakaoService.getToken(code), HttpStatus.OK);
    }

}
