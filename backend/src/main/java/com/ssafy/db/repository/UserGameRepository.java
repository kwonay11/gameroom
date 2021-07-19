package com.ssafy.db.repository;

import com.ssafy.db.entity.UserGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGameRepository extends JpaRepository<UserGame,Long> {
}
