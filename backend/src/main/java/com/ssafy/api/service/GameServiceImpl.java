package com.ssafy.api.service;

import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service("gameCategoryService")
public class GameServiceImpl implements GameService {
    @Autowired
    GameCategoryRepository gameCategoryRepository;

    @Autowired
    GameKeywordRepository gameKeywordRepository;

    @Autowired
    GameHistoryRepository gameHistoryRepository;

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    GameRepository gameRepository;

    @Override
    public List<GameCategory> getGameCategoryList() {
        return gameCategoryRepository.findAll();
    }

    @Override
    public GameCategory getGameCategoryById(Long id) {
        return gameCategoryRepository.findById(id).get();
    }

    @Override
    public String getKeywordRand(Long category) {
        List<String> keywordList = gameKeywordRepository.findKeywordByGameCategory(category);
        Random rand = new Random();
        return keywordList.get(rand.nextInt(keywordList.size()));
    }

    @Override
    public Game saveGame(Long conference, Long gameCategory) {
        return gameRepository.save(Game.builder()
                .conference(conferenceRepository.findById(conference).get())
                .gameCategory(gameCategoryRepository.findById(gameCategory).get())
                .build());
    }

    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public GameHistory saveGameHistory(User user, Game game, int action, int ranking) {
        return gameHistoryRepository.save(GameHistory.builder()
                .user(user)
                .game(game)
                .action(action)
                .ranking(ranking)
                .build());
    }

    @Override
    public List<GameHistory> getGameHistoryListByGameAndRanking(Game game, int rankging) {
        return gameHistoryRepository.findGameHistoriesByGameAndRanking(game, rankging);
    }
}
