package com.ssafy.api.service;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.repository.ConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("ConferenceService")
public class ConferenceServiceImpl implements ConferenceService{
    @Autowired
    ConferenceRepository conferenceRepository;

    @Override
    public Optional<Conference> getConferenceById(Long id) {
        return conferenceRepository.findById(id);
    }
}
