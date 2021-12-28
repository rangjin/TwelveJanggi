package com.rangjin.twelvejanggi.domain.player.controller.response;

import com.rangjin.twelvejanggi.domain.player.entity.PlayerEntity;
import com.rangjin.twelvejanggi.domain.gameRecord.controller.response.GameRecordResponseDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class PlayerEntityResponseDto {

    private String username;

    private String password;

    private List<GameRecordResponseDto> gameRecordDtoList;

    public PlayerEntityResponseDto(PlayerEntity player) {
        this.username = player.getUsername();
        this.password = player.getPassword();
        this.gameRecordDtoList = player.getGameRecordList() == null ? null :
                player.getGameRecordList().stream().map(GameRecordResponseDto::new).collect(Collectors.toList());
    }

}
