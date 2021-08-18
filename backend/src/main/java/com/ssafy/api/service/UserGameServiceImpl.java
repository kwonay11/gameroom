package com.ssafy.api.service;

import com.ssafy.db.entity.Game;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserGame;
import com.ssafy.db.repository.UserGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userGameService")
public class UserGameServiceImpl implements UserGameService{
    @Autowired
    UserGameRepository userGameRepository;

    @Override
    public UserGame saveUserGame(User user, Game game) {
        return userGameRepository.save(UserGame.builder()
                .user(user)
                .game(game)
                .build());
    }

    @Override
    public void deleteUserGame(User user, Game game) {
        userGameRepository.delete(userGameRepository.findByUserAndGame(user, game));
    }

    @Override
    public Optional<UserGame> getUserGameByUser(User user) {
        return userGameRepository.findByUser(user);
    }
}
