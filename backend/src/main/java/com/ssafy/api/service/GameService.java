package com.ssafy.api.service;

import com.ssafy.db.entity.Game;
import com.ssafy.db.entity.GameCategory;
import com.ssafy.db.entity.GameHistory;
import com.ssafy.db.entity.User;

import java.util.List;

public interface GameService {
    List<GameCategory> getGameCategoryList();
    GameCategory getGameCategoryById(Long id);
    String getKeywordRand(Long category);
    Game saveGame(Long conference, Long gameCategory);
    Game saveGame(Game game);
    GameHistory saveGameHistory(User user, Game game, int action, int ranking);
    List<GameHistory> getGameHistoryListByGameAndRanking(Game game, int rankging);
}
