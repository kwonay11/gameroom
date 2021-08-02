package com.ssafy.api.response;

public interface ConferenceMapping{
    long getId();
    UserMapping getOwner();
    GameCategoryMapping getGameCategory();
    String getTitle();
    int getMaxUser();
}
