package com.ssafy.api.service;
import com.ssafy.api.response.ConferenceMapping;
import com.ssafy.db.entity.ConferenceHistory;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserConference;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.repository.ConferenceRepository;
import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 방 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */

@Service("conferenceService")
@RequiredArgsConstructor
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

    @Autowired
    GameCategoryRepository gameCategoryRepository;

    @Autowired
    UserGameRepository userGameRepository;

    @Override
    public Optional<Conference> getConferenceById(Long id) {
        return conferenceRepository.findById(id);
    }

    @Override
    public Optional<List<ConferenceMapping>> getConferenceByActiveTrue() { return conferenceRepository.findByActiveTrue(); }

    @Override
    public ConferenceHistory exitConference(User user, Long conferenceId){
            // 방 정보(user_conference) 삭제 (userId, RoomId를 통한 삭제)
            Optional<UserConference> conference = userConferenceRepository.findByUserId(user.getId());

            // 방의 마지막사람은 컨퍼런스 종료시간+ isactive = false로 하고 방을 없애준다.
            long count = userConferenceRepository.countByConferenceId(conferenceId);
            if (count == 1L) {
                userConferenceRepository.delete(conference.get());
                Thread t = new ExitConferenceThread(user, conferenceId, this);
                t.start();
            }
            Optional<UserGame> userGame = userGameRepository.findByUserId(user.getId());
            System.out.println(userGame);
            // userGame 삭제
            if (userGame.isPresent()) { userGameRepository.delete(userGame.get()); }


        //컨퍼런스 테이블에 남겨두기 , create(0), join(1), exit(2)
            ConferenceHistory conferenceHistory = ConferenceHistory.builder()
                    .conference(conference.get().getConference())
                    .action(2)
                    .user(user)
                    .build();

        return conferenceHistoryRepository.save(conferenceHistory);
    }

    @Override
    public Long register(ConferenceRegisterPostReq dto) {
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

        ConferenceHistory conferenceHistory =ConferenceHistory.builder()
                .conference(result)
                .user(user)
                .action(0)
                .build();
        conferenceHistoryRepository.save(conferenceHistory);

        return result.getId();
    }

    @Override
    public Conference saveConference(Conference conference) {
        return conferenceRepository.save(conference);
    }

    @Override
    public String getPasswordById(Long id) {
        return conferenceRepository.findById(id).get().getPassword();
    }

}


  
