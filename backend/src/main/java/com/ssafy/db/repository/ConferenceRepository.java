package com.ssafy.db.repository;

import com.ssafy.db.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference,Long> {
    Long findById(long id);
}
