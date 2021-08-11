package com.ssafy.api.service;

import com.ssafy.db.entity.UserConference;

import java.util.List;

public interface UserConferenceService {
    List<UserConference> getUserConferenceByConferenceId(long conferenceId);

    void addUserConference(UserConference userConference);

    Long countByConferenceId(long conferenceId);
}
