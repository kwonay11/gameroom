package com.ssafy.api.service;

import com.ssafy.db.entity.Conference;

import java.util.Optional;

public interface ConferenceService {
    Optional<Conference> getConferenceById(Long id);
}
