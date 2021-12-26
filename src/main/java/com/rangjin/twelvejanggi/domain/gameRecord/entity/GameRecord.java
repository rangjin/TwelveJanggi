package com.rangjin.twelvejanggi.domain.gameRecord.entity;

import com.rangjin.twelvejanggi.domain.orderRecord.entity.OrderRecord;
import com.rangjin.twelvejanggi.domain.game.model.game.Game;
import com.rangjin.twelvejanggi.domain.game.model.player.PlayerType;
import com.rangjin.twelvejanggi.domain.player.entity.PlayerEntity;
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

    @ManyToOne
    private PlayerEntity white;

    @ManyToOne
    private PlayerEntity black;

    private PlayerType winner;

    @OneToMany(mappedBy = "gameRecord")
    private List<OrderRecord> orderList;

    public GameRecord(Game game, PlayerEntity white, PlayerEntity black) {
        this.gameId = game.getGameId();
        this.white = white;
        this.black = black;
        this.winner = game.getWinner();
        this.orderList = new ArrayList<>();
    }

}
