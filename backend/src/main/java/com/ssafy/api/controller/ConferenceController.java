package com.ssafy.api.controller;

import com.ssafy.api.request.ConferenceRegisterPostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.service.ConferenceService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conference;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.ssafy.common.auth.SsafyUserDetails;
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
    public ResponseEntity<?> register(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) ConferenceRegisterPostReq registerPostReq,
            @ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String userId = userDetails.getUsername();

        registerPostReq.setUserid(userId);

        return new ResponseEntity<>(conferenceService.register(registerPostReq), HttpStatus.CREATED);




    }
}
