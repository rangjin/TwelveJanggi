package com.rangjin.twelvejanggi.global.security.component;

import com.rangjin.twelvejanggi.domain.user.controller.response.UserDto;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {

    public UserDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        String role = String.valueOf(oAuth2User.getAuthorities());

        return UserDto.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .role(role.substring(1, role.length() - 1))
                .build();
    }

}
