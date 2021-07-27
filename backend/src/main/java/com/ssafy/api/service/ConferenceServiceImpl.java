package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.ConferenceHistoryRepository;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.db.repository.UserConferenceRepository;
import com.ssafy.db.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityListeners;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    ConferenceRepository conferenceRepository;

    @Autowired
    ConferenceHistoryRepository conferenceHistoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserConferenceRepository userConferenceRepository;


    @Override
    public int register(ConferenceRegisterPostReq dto) {
        User user = userRepository.findByUserId(dto.getUserid()).get();
        Conference conference = Conference.builder()
                .owner(user)
                .title(dto.getTitle())
                .password(dto.getPassword())
                .maxUser(dto.getMaxUser())
                .build();
        conferenceRepository.save(conference);


        Optional<Conference> byOwner = conferenceRepository.findByOwner(user);
        UserConference userConference = UserConference.builder()
                .conference(byOwner.get())
                .user(user)
                .build();
        userConferenceRepository.save(userConference);
//
//        ConferenceHistory conferenceHistory =ConferenceHistory.builder()
//                .conference(conference)
//                .user(user)
//                .action(1)
//                .build();
//        conferenceHistoryRepository.save(conferenceHistory);

        return conferenceRepository.save(conference).getMaxUser();


    }
}
