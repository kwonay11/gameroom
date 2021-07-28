package com.ssafy.api.controller;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.ConferenceRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.ssafy.common.auth.SsafyUserDetails;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/conferences")
public class ConferenceController {
    @Autowired
    private ConferenceService conferenceService;

    @PostMapping()
    @ApiOperation(value = "방 정보생성", notes = "컨퍼런스 방 정보 생성 한다. ")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<ConferenceRes> register(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) ConferenceRegisterPostReq registerPostReq,
            @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        registerPostReq.setUserid(userId);
        int result = conferenceService.register(registerPostReq);

        return ResponseEntity.ok(ConferenceRes.of(201,"Success",result));


    }
}
