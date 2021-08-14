package com.ssafy.api.controller;
import com.ssafy.api.request.ConferencePasswordGetReq;
import com.ssafy.api.response.*;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserConferenceService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.api.service.ConferenceHistoryService;
import com.ssafy.db.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiParam;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "컨퍼런스 API", tags = {"Conference"})


@RestController
@RequestMapping("/api/conferences")
public class ConferenceController {
    @Autowired
    ConferenceService conferenceService;
    @Autowired
    UserConferenceService userConferenceService;

    @Autowired
    ConferenceHistoryService conferenceHistoryService;

    @GetMapping()
    @ApiOperation(value = "대기 방 조회", notes = "인원 수가 많이 충족된 방 순으로 response")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "대기 중인 방 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getConferences(@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) {
        /**
         * conference 테이블에서 is_active가 true인 모든 방 검색
         * user_conference 테이블을 이용하여 방마다 인원을 구함
         * max_user - 인원 수가 적은 테이블을 우선으로 list를 정렬한 후 response 해줌
         */
        Optional<List<ConferenceMapping>> conferenceMappings = conferenceService.getConferenceByActiveTrue();
        if(!conferenceMappings.isPresent()) // active가 true인 방이 없을 경우
            return ResponseEntity.status(401).body(BaseResponseBody.of(404, "fail"));
        List<ConferenceMapping> list = conferenceMappings.get();
        List<Object[]> list2 = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            ConferenceMapping conferenceMapping = list.get(i);
            if(keyword.equals("")) {  // 검색이 아닌 경우
                list2.add(new Object[]{conferenceMapping, conferenceMapping.getMaxUser() - userConferenceService.countByConferenceId(conferenceMapping.getId())});
                continue;
            }
            if(conferenceMapping.getTitle().replace(" ", "").contains(keyword) || conferenceMapping.getOwner().getNickname().contains(keyword))  // 검색 키워드가 제목 혹은 방장 닉네임에 포함될 경우
                list2.add(new Object[]{conferenceMapping, conferenceMapping.getMaxUser() - userConferenceService.countByConferenceId(conferenceMapping.getId())});
        }
        Collections.sort(list2, Comparator.comparingLong(o -> (Long) o[1]));
        List<ConferenceInfoRes> res = new ArrayList<>();
        for(int i = 0; i < list2.size(); i++) {
            if((long)list2.get(i)[1] <= 0)
                continue;
            ConferenceMapping conferenceMapping = (ConferenceMapping) list2.get(i)[0];
            // 방 정보를 conferenceInfoRes 객체에 저장 후 response 시켜줌
            ConferenceInfoRes conferenceInfoRes = new ConferenceInfoRes();

            conferenceInfoRes.setId(conferenceMapping.getId());
            conferenceInfoRes.setOwnerNickname(conferenceMapping.getOwner().getNickname());
            conferenceInfoRes.setTitle(conferenceMapping.getTitle());
            conferenceInfoRes.setGameId(conferenceMapping.getGameCategory().getId());
            conferenceInfoRes.setGameName(conferenceMapping.getGameCategory().getName());
            conferenceInfoRes.setMaxUser(conferenceMapping.getMaxUser());
            conferenceInfoRes.setNowUser(conferenceMapping.getMaxUser() - (long)list2.get(i)[1]);
            boolean privateRoom = true;
            if(conferenceMapping.getPassword() == null || conferenceMapping.getPassword().equals(""))
                privateRoom = false;
            conferenceInfoRes.setPrivateRoom(privateRoom);

            res.add(conferenceInfoRes);
        }
        return ResponseEntity.status(200).body(res);
    }


    @GetMapping("/{conferenceId}")
    @ApiOperation(value = "게임 방 접속", notes = "인원 수에 여유가 있다면 게임 방에 접속")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> enterConference(@PathVariable Long conferenceId,
                                                                      @ApiIgnore Authentication authentication,
                                                                      ConferencePasswordGetReq conferencePasswordGetReq) {
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

        if(conferenceService.getPasswordById(conferenceId) != null &&
                !conferenceService.getPasswordById(conferenceId).equals("") &&
                !conferencePasswordGetReq.getPassword().equals(conferenceService.getPasswordById(conferenceId))) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(403, "false"));
        }

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

    @DeleteMapping(value = "/{conferenceid}")
    @ApiOperation(value = "게임 방 나가기")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> exitConference(@PathVariable("conferenceid") Long conferenceid,
                                             @ApiIgnore Authentication authentication){
        //Token이용해 userid가져오기
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userDetails.getUser();
        //관련 confencehistory삭제 & history저장
        conferenceService.exitConference(user, conferenceid);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "true"));
        }

    @GetMapping("/info/{conferenceId}")
    @ApiOperation(value = "게임 방 정보", notes = "게임 방 정보를 response")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getGameRoomInfo(@PathVariable Long conferenceId) {
        Optional<Conference> optionalConference = conferenceService.getConferenceById(conferenceId);
        if(!optionalConference.isPresent() || !optionalConference.get().isActive())  // 방이 존재하지 않거나 is_active가 false일 경우
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "false"));
        Conference conference = optionalConference.get();
        GameRoomInfoRes res = GameRoomInfoRes.builder()
                                .title(conference.getTitle())
                                .gameId(conference.getGameCategory().getId())
                                .gameName(conference.getGameCategory().getName())
                                .gameSummary(conference.getGameCategory().getSummary())
                                .ownerNicknames(conference.getOwner().getNickname())
                                .maxUser(conference.getMaxUser())
                                .build();
        return ResponseEntity.status(200).body(res);
    }

    @PostMapping() 
    @ApiOperation(value = "방 정보생성", notes = "컨퍼런스 방 정보 생성 한다. ")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<?> register(
            @RequestBody @ApiParam(value = "방 정보", required = true) ConferenceRegisterPostReq registerPostReq,
            @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        if(registerPostReq.getMaxUser() < 2 ||  // 최대 인원 수가 2 이하거나
                registerPostReq.getTitle() == null ||  // 방제가 null 이거나
                registerPostReq.getTitle().equals(""))  // 공백일 경우 방을 만들지 않고 401 response
            return ResponseEntity.ok(ConferenceRes.of(401,"fail"));

        registerPostReq.setUserid(userId);
        Long result = conferenceService.register(registerPostReq);

        return ResponseEntity.ok(ConferenceRes.of(201,"Success", result));


    }
}
