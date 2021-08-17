package com.ssafy.db.repository;

import com.ssafy.db.entity.Game;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGameRepository extends JpaRepository<UserGame,Long> {
    UserGame findByUser(User user);
    UserGame findByUserAndGame(User user, Game game);
    Optional<UserGame> findByUserId(Long userId);
}
