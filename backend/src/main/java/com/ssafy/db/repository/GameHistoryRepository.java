package com.ssafy.db.repository;

import com.ssafy.db.entity.Game;
import com.ssafy.db.entity.GameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory,Long> {
    List<GameHistory> findGameHistoriesByGameAndRanking(Game game, int ranking);
}
