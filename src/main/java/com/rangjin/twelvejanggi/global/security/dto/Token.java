package com.rangjin.twelvejanggi.global.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Token {

    private String token;

    private String refreshToken;

}
