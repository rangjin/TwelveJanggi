package com.rangjin.twelvejanggi.global.security.configuration;

import com.rangjin.twelvejanggi.global.security.service.CustomOAuth2UserService;
import com.rangjin.twelvejanggi.global.security.filter.JwtAuthFilter;
import com.rangjin.twelvejanggi.global.security.handler.OAuth2SuccessHandler;
import com.rangjin.twelvejanggi.global.security.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler successHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                        .authorizeRequests()
                                .antMatchers("/game/**").hasRole("USER")
                                .anyRequest().permitAll()
                .and()
                        .addFilterBefore(new JwtAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                        .oauth2Login()
                                .successHandler(successHandler)
                                .userInfoEndpoint().userService(oAuth2UserService);

    }

}
