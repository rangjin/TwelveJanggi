package com.rangjin.twelvejanggi.domain.user.entity;

import com.rangjin.twelvejanggi.domain.gameRecord.entity.GameRecord;
import com.rangjin.twelvejanggi.domain.user.controller.response.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String picture;

    private String role;

    private String refreshToken;

    @OneToMany
    private List<GameRecord> gameRecordList;

    public void update(UserDto userDto) {
        this.email = userDto.getEmail();
        this.name = userDto.getName();
        this.picture = userDto.getPicture();
        this.role = userDto.getRole();
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
