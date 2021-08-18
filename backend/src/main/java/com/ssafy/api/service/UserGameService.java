package com.ssafy.api.service;

import com.ssafy.db.entity.Game;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserGame;

import java.util.Optional;

public interface UserGameService {
    UserGame saveUserGame(User user, Game game);
    void deleteUserGame(User user, Game game);
    Optional<UserGame> getUserGameByUser(User user);
}
