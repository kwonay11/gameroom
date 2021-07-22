package com.ssafy.api.response;

public interface WinRateMapping {
    GameCategoryMapping getGameCategory();
    int getFirstRanked(); // 1등한 횟수
    int getGameCount(); // 전체 게임횟수
}
