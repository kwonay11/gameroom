package com.ssafy.api.service;

import com.ssafy.db.entity.GameCategory;

import java.util.List;

public interface GameCategoryService {
    List<GameCategory> getGameCategoryList();
}
