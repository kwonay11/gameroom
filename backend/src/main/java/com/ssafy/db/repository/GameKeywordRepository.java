package com.ssafy.db.repository;

import com.ssafy.db.entity.GameKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public interface GameKeywordRepository extends JpaRepository<GameKeyword,Long> {
    @Query("SELECT keyword FROM GameKeyword Where gameCategory.id = :category")
    List<String> findKeywordByGameCategory(
            @Param("category") Long category
    );
}
