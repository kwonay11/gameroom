package com.ssafy.api.service;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.repository.ConferenceRepository;

/**
 * 방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */

@Service("conferenceService")
public class ConferenceServiceImpl implements ConferenceService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRepositorySupport userRepositorySupport;
    @Autowired
    ConferenceRepository conferenceRepository;
    @Autowired
    ConferenceHistoryRepository conferenceHistoryRepository;
    @Autowired
    UserConferenceRepository userConferenceRepository;

    @Override
    public Optional<Conference> getConferenceById(Long id) {
        return conferenceRepository.findById(id);
    }

    @Override
    public ConferenceHistory exitConference(String userId, Long conferenceId){
        // 방 정보(user_conference) 삭제 (userId, RoomId를 통한 삭제)
        Optional<UserConference> conference = userConferenceRepository.findById(conferenceId);
        User user = userRepositorySupport.findUserByUserId(userId).get();
        userConferenceRepository.delete(conference.get());
        //컨퍼런스 테이블에 남겨두기 , create(0), join(1), exit(2)
        ConferenceHistory conferenceHistory = ConferenceHistory.builder()
                                                .conference(conference.get().getConference())
                                                .action(2)
                                                .user(user)
                                                .build();
        return conferenceHistoryRepository.save(conferenceHistory);

    }
}