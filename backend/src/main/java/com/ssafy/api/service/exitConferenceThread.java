package com.ssafy.api.service;

import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserConference;

import java.util.Optional;

public class exitConferenceThread extends Thread {
    User user;
    Long conferenceId;
    ConferenceServiceImpl conferenceService;
    public exitConferenceThread(User user, Long conferenceId, ConferenceServiceImpl conferenceService) {
        this.user = user;
        this.conferenceId = conferenceId;
        this.conferenceService = conferenceService;
    }
    public void run() {
        try {
            System.out.println("try확인");
            Thread.sleep(1000);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        Optional<UserConference> userConference = conferenceService.userConferenceRepository.findByUserId(user.getId());
        if (!userConference.isPresent()) {
            Conference conference = conferenceService.conferenceRepository.findById(conferenceId).get();
            conference.setActive(false);
            conferenceService.conferenceRepository.save(conference);
        }
    }
}
