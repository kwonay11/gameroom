package com.ssafy.db.repository;

import com.ssafy.db.entity.GameCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameCategoryRepository extends JpaRepository<GameCategory,Long> {
}
