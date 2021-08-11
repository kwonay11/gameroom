package com.ssafy.api.service;

import com.ssafy.db.entity.GameCategory;
import com.ssafy.db.repository.GameCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gameCategoryService")
public class GameCategoryServiceImpl implements GameCategoryService {
    @Autowired
    GameCategoryRepository gameCategoryRepository;

    @Override
    public List<GameCategory> getGameCategoryList() {
        return gameCategoryRepository.findAll();
    }
}
