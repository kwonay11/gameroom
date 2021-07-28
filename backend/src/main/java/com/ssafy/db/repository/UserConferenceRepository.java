package com.ssafy.db.repository;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserConference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface UserConferenceRepository extends JpaRepository<UserConference,Long> {
    List<UserConference> findByConferenceId(long conferenceId);
}
