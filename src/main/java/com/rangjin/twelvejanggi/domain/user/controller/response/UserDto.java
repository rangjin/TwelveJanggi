package com.rangjin.twelvejanggi.domain.user.controller.response;

import com.rangjin.twelvejanggi.domain.user.entity.User;
import com.rangjin.twelvejanggi.domain.gameRecord.controller.response.GameRecordResponseDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
public class UserDto {

    private String email;

    private String name;

    private String picture;

    private String role;

    private List<GameRecordResponseDto> gameRecordDtoList;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.picture = user.getPicture();
        this.role = user.getRole();
        this.gameRecordDtoList = user.getGameRecordList() == null ? null :
                user.getGameRecordList().stream().map(GameRecordResponseDto::new).collect(Collectors.toList());
    }

}
