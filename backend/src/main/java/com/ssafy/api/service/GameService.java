package com.ssafy.api.service;

import com.ssafy.db.entity.GameCategory;
import com.ssafy.db.entity.GameKeyword;

import java.util.List;

public interface GameService {
    List<GameCategory> getGameCategoryList();
    List<String> getGameKeywordList(Long category);
}
