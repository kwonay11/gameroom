package com.ssafy.api.service;

import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.UserConferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserConferenceService")
public class UserConferenceServiceImpl implements UserConferenceService {
    @Autowired
    UserConferenceRepository userConferenceRepository;

    @Override
    public List<UserConference> getUserConferenceByConferenceId(long conferenceId) {
        return userConferenceRepository.findByConferenceId(conferenceId);
    }

    @Override
    public void addUserConference(UserConference userConference) {
        userConferenceRepository.save(userConference);
    }

    @Override
    public Long countByConferenceId(long conferenceId) {
        return userConferenceRepository.countByConferenceId(conferenceId);
    }
}
