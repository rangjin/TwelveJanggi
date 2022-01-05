package com.rangjin.twelvejanggi.domain.user.service;

import com.rangjin.twelvejanggi.global.dto.KakaoAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${url.base}")
    private String baseUrl;

    @Value("${social.kakao.client_id}")
    private String kakaoClientId;

    @Value("${social.kakao.redirect}")
    private String kakaoRedirectUri;

    @Value("${social.kakao.url.token}")
    private String kakaoTokenUri;

    public KakaoAuth getToken(String code) {
        var header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", baseUrl + kakaoRedirectUri);
        params.add("code", code);

        var request = new HttpEntity<>(params, header);
        var response = new RestTemplate().exchange(
                kakaoTokenUri,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<KakaoAuth>(){}
        );

        return response.getBody();
    }

}
