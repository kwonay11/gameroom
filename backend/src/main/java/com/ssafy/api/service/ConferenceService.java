package com.ssafy.api.service;
import com.ssafy.api.response.ConferenceMapping;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.Conference;

import java.util.List;
import java.util.Optional;
import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.GameCategory;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
/**
 * 방 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의
 */

public interface ConferenceService {
    ConferenceHistory exitConference(User user, Long ConferenceId); //방 나가기

    Optional<Conference> getConferenceById(Long id);
    Optional<List<ConferenceMapping>> getConferenceByActiveTrue();
    Long register(ConferenceRegisterPostReq dto);
    Conference saveConference(Conference conference);
}


