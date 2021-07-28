package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
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

    @Autowired
    GameCategoryRepository gameCategoryRepository;


    @Override
    public int register(ConferenceRegisterPostReq dto) {
        User user = userRepository.findByUserId(dto.getUserid()).get();
        GameCategory gameCategory = gameCategoryRepository.findById(dto.getGamecategory()).get();
        Conference conference = Conference.builder()
                .owner(user)
                .gameCategory(gameCategory)
                .title(dto.getTitle())
                .password(dto.getPassword())
                .maxUser(dto.getMaxUser())
                .build();
        Conference result = conferenceRepository.save(conference);

        UserConference userConference = UserConference.builder()
                .conference(result)
                .user(user)
                .build();
        userConferenceRepository.save(userConference);

        ConferenceHistory conferenceHistory =ConferenceHistory.builder()
                .conference(result)
                .user(user)
                .action(0)
                .build();
        conferenceHistoryRepository.save(conferenceHistory);

        return result.getMaxUser();


    }
}
