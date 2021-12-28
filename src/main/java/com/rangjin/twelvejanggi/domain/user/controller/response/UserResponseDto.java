package com.rangjin.twelvejanggi.domain.user.controller.response;

import com.rangjin.twelvejanggi.domain.user.entity.User;
import com.rangjin.twelvejanggi.domain.gameRecord.controller.response.GameRecordResponseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class UserResponseDto {

    private String username;

    private String password;

    private List<GameRecordResponseDto> gameRecordDtoList;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.gameRecordDtoList = user.getGameRecordList() == null ? null :
                user.getGameRecordList().stream().map(GameRecordResponseDto::new).collect(Collectors.toList());
    }

}
