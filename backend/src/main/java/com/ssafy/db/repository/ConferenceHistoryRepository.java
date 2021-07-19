package com.ssafy.db.repository;

import com.ssafy.db.entity.ConferenceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConferenceHistoryRepository extends JpaRepository<ConferenceHistory,Long> {
}
