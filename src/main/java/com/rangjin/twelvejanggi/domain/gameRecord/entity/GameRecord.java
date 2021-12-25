package com.rangjin.twelvejanggi.domain.gameRecord.entity;

import com.rangjin.twelvejanggi.domain.orderRecord.entity.OrderRecord;
import com.rangjin.twelvejanggi.domain.game.model.game.Game;
import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gameId;

    private String white;

    private String black;

    private PlayerType winner;

    @OneToMany(mappedBy = "gameRecord")
    private List<OrderRecord> orderList;

    public GameRecord(Game game) {
        this.gameId = game.getGameId();
        this.white = game.getWhite().getName();
        this.black = game.getBlack().getName();
        this.winner = game.getWinner();
        this.orderList = new ArrayList<>();
    }

}
