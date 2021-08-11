package com.ssafy.api.response;

public interface ConferenceMapping{
    Long getId();
    UserMapping getOwner();
    GameCategoryMapping getGameCategory();
    String getTitle();
    int getMaxUser();
    String getPassword();
}
