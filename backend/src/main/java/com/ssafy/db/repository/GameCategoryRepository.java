package com.ssafy.db.repository;

import com.ssafy.db.entity.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameCategoryRepository extends JpaRepository<GameCategory,Long> {
}
