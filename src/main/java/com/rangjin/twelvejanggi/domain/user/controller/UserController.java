package com.rangjin.twelvejanggi.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;

    @Value("${url.base}")
    private String baseUrl;

    @Value("${social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${social.kakao.redirect}")
    private String kakaoRedirectUri;

    @GetMapping("/social/login")
    public ModelAndView socialLogin(ModelAndView mav) {
        String kakaoLoginUri = env.getProperty("social.kakao.url.login")
                + "?response_type=code&client_id=" + kakaoClientId
                + "&redirect_uri=" + baseUrl + kakaoRedirectUri;
        mav.addObject("kakaoLoginUrl", kakaoLoginUri);
        mav.setViewName("social/login");

        return mav;
    }

    @GetMapping("/kakao/redirect")
    public ModelAndView kakaoRedirect(ModelAndView mav) {

        mav.setViewName("social/kakaoRedirect");

        return mav;
    }

}
