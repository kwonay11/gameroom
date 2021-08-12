package com.ssafy.api.service;

import com.ssafy.db.entity.GameCategory;
import com.ssafy.db.entity.GameKeyword;
import com.ssafy.db.repository.GameCategoryRepository;
import com.ssafy.db.repository.GameKeywordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gameCategoryService")
public class GameServiceImpl implements GameService {
    @Autowired
    GameCategoryRepository gameCategoryRepository;

    @Autowired
    GameKeywordRepository gameKeywordRepository;

    @Override
    public List<GameCategory> getGameCategoryList() {
        return gameCategoryRepository.findAll();
    }

    @Override
    public List<String> getGameKeywordList(Long category) {
        return gameKeywordRepository.findKeywordByGameCategory(category);
    }
}
