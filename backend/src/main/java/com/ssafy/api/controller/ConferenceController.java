package com.ssafy.api.controller;


import com.ssafy.api.service.ConferenceService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="방 API", tags = {"conference"})
@RestController
@RequestMapping("/conferences")
public class ConferenceController {
    @Autowired
    ConferenceService conferenceService;

    @DeleteMapping(value = "/{conferenceid}")
    @ApiOperation(value = "회원 방 정보 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> exitConference(@PathVariable("conferenceid") Long conferenceid,
                                             @ApiIgnore Authentication authentication){
        //Token이용해 userid가져오기
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userid = userDetails.getUsername();
        //관련 confencehistory삭제 & history저장
        conferenceService.exitConference(userid, conferenceid);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "true"));
        }
}
