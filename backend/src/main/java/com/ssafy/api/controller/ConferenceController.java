package com.ssafy.api.controller;

import com.ssafy.api.service.ConferenceHistoryService;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserConferenceService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"Conference"})
@RestController
@RequestMapping("/conferences")
public class ConferenceController {
    @Autowired
    ConferenceService conferenceService;
    @Autowired
    UserConferenceService userConferenceService;
    @Autowired
    ConferenceHistoryService conferenceHistoryService;

    @GetMapping("/{conferenceId}")
    @ApiOperation(value = "게임 방 접속", notes = "인원 수에 여유가 있다면 게임 방에 접속")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> enterConference(@PathVariable Long conferenceId,
                                                                      @ApiIgnore Authentication authentication) {
        /**
         * conferenceId로 conference 테이블에서 검색
         * 방이 존재하지 않거나 is_active가 false 일 경우 404 에러발생
         * 방 인원이 꽉찼을 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         * -> 현재 방 인원을 확인하기 위해 user_conference 테이블에서 conferenceId를 검색 후 갯수를 확인해야함
         * 위에 해당하지 않는다면 user_conference와 conference_history에 데이터를 넣어주고 200을 response 해줌
         */
        Optional<Conference> conference = conferenceService.getConferenceById(conferenceId);
        if(!conference.isPresent() || !conference.get().isActive())  // 방이 존재하지 않거나 is_active가 false일 경우
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "false"));

        List<UserConference> userConferenceList = userConferenceService.getUserConferenceByConferenceId(conferenceId);
        if(userConferenceList.size() >= conference.get().getMaxUser())  // 방의 인원이 초과된 경우
            return ResponseEntity.status(200).body(BaseResponseBody.of(403, "false"));

        // user_conference에 user 추가
        UserConference userConference = new UserConference();
        userConference.setConference(conference.get());

        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails(); //JWT 토큰을 통한 유저 정보 가져오기
        userConference.setUser(userDetails.getUser());
        userConferenceService.addUserConference(userConference);

        // conference_history에 데이터 추가
        ConferenceHistory conferenceHistory = new ConferenceHistory();
        conferenceHistory.setConference(conference.get());
        conferenceHistory.setUser(userDetails.getUser());
        conferenceHistory.setAction(1);
        conferenceHistoryService.addConferenceHistory(conferenceHistory);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "true"));
    }
}
