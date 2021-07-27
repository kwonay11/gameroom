package com.ssafy.db.repository;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConferenceRepository extends JpaRepository<Conference,Long> {
    Optional<Conference> findByOwner(User user);
}
