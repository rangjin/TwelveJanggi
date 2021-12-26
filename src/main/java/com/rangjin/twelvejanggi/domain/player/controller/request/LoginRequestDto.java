package com.rangjin.twelvejanggi.domain.player.controller.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {

    private String username;

    private String password;

}