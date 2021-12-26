package com.rangjin.twelvejanggi.domain.player.controller.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {

    private String username;

    private String password;

    private String rePassword;

}
