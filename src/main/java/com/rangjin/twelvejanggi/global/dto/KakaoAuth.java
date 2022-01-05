package com.rangjin.twelvejanggi.global.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAuth {

    private String access_token;

    private String token_type;

    private String refresh_token;

    private long expires_in;

    private String scope;

    private long refresh_token_expires_in;

}
