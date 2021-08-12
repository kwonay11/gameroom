package com.ssafy.db.repository;

import com.ssafy.db.entity.GameKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameKeywordRepository extends JpaRepository<GameKeyword,Long> {
    List<String> findKeywordByGameCategory(Long category);
}
