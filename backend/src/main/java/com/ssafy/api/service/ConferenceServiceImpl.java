package com.ssafy.api.service;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.ConferenceRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

    @Autowired
    private ConferenceRepository conferenceRepository;


    @Override
    public int register(ConferenceRegisterPostReq dto) {
        Conference conference = Conference.builder()
                .owner(User.builder().userId(dto.getUserid()).build())
//                .callStartTime(new LocalDateTime())
                .title(dto.getTitle())
                .password(dto.getPassword())
                .maxUser(dto.getMaxUser())
                .build();
        return conferenceRepository.save(conference).getMaxUser();


    }
}
