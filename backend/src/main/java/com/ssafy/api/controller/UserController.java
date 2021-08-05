package com.ssafy.api.controller;

import com.ssafy.api.request.UserUpdateNicknamePutReq;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.WinRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.ssafy.api.request.UserUpdatePasswordPostReq;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "유저 API", tags = {"User"})
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@PostMapping()
	@ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.") 
    @ApiResponses({
        @ApiResponse(code = 201, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<? extends BaseResponseBody> register(
			@RequestBody @ApiParam(value="회원가입 정보", required = true) UserRegisterPostReq registerInfo) {

		if(userRepository.findByUserId(registerInfo.getId()).isPresent())  // 같은 id의 회원이 이미 존재하는 경우
			return ResponseEntity.status(403).body(BaseResponseBody.of(403, "already exist"));

		if(registerInfo.getId().equals("") || registerInfo.getPassword().equals("") || registerInfo.getNickname().equals(""))  // 데이터 유효성검사 (수정해야함함)
		return ResponseEntity.status(403).body(BaseResponseBody.of(403, "invalidate data"));

		//임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
		User user = userService.createUser(registerInfo);
		
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "true"));
	}

	@PostMapping("/check")
	@ApiOperation(value = "아이디 중복체크", notes = "<strong>아이디</strong> 중복체크")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> idCheck(
			@RequestBody @ApiParam(value="아이디", required = true) UserRegisterPostReq registerInfo) {

		if(userRepository.findByUserId(registerInfo.getId()).isPresent())  // 같은 id의 회원이 이미 존재하는 경우
			return ResponseEntity.status(403).body(BaseResponseBody.of(403, "already exist"));

		return ResponseEntity.status(200).body(BaseResponseBody.of(200, "true"));
	}

	@Autowired
	WinRateRepository winRateRepository;

	@GetMapping("/{id}")
	@ApiOperation(value = "회원 정보 조회", notes = "검색한 회원의 정보를 응답한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "성공"),
        @ApiResponse(code = 401, message = "인증 실패"),
        @ApiResponse(code = 404, message = "사용자 없음"),
        @ApiResponse(code = 500, message = "서버 오류")
    })
	public ResponseEntity<UserRes> getUserInfo(@ApiIgnore Authentication authentication, @PathVariable String id) {
		/**
		 * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
		 * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
		 */
		//SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
		//String userId = userDetails.getUsername();
		User user = userService.getUserByUserId(id);
		UserRes res = new UserRes();
		res.setNickname(user.getNickname());
		res.setExp((user.getExp()));
		res.setWinRateList(winRateRepository.findByUserId(user.getId()));

		return ResponseEntity.status(200).body(res);
	}

	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "회원 본인 정보 삭제", notes = "로그인한 회원 본인의 정보를 삭제한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<? extends BaseResponseBody> remove(@PathVariable("id") String id ,
															 @ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails(); //JWT토큰을 통한 유저정보 가져오기
		String username = userDetails.getUsername(); //username: 현재 로그인된 유저 닉네임
		if (username.equals(id)) { // 현재 로그인된 유저와 삭제하려는 유저가 같으면 service 실행

			userService.removeUser(id);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}
		return ResponseEntity.status(401).body(BaseResponseBody.of(401,"fail"));
	}
	@PutMapping(value = "/nickname/{id}")
	@ApiOperation(value = "회원 본인 닉네임 수정", notes = "로그인한 회원 본인의 닉네임을 수정한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 500, message = "서버 오류")
	})
	public ResponseEntity<?> modifyuserNickname(@PathVariable("id") String id,
										 @RequestBody UserUpdateNicknamePutReq userDTO,
										 @ApiIgnore Authentication authentication) {

		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails(); //JWT 토큰을 통한 유저 정보 가져오기
		String username = userDetails.getUsername(); // username: 현재 로그인된 유저 닉네임
		if (username.equals(id)) { //로그인된 유저와 수정하려는 유저가 같으면 service 실행
			User user = userService.getUserByUserId(id);
			userDTO.setUserId(id);
			userService.modifyUser(userDTO);
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "true"));
		}
		return ResponseEntity.status(401).body(BaseResponseBody.of(401, "fail"));
	}

	//비밀번호 수정
	@PutMapping("/{id}")
	@ApiOperation(value= "비밀 번호 수정")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공"),
			@ApiResponse(code = 401, message = "인증 실패"),
			@ApiResponse(code = 404, message = "사용자 없음"),
			@ApiResponse(code = 500, message = "회원정보 수정 실패")
	})
	private ResponseEntity<?> updateUserPassword(
//			id 받기 위해서 @PathVariable사용
			@ApiParam(value = "유저 id", required = true, example = "ssafy") @PathVariable String id,
			@ApiParam(value = "업데이트 할 유저 정보", required = true) @RequestBody UserUpdatePasswordPostReq userReq,
																	 @ApiIgnore Authentication authentication) {
		SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails(); //jwt토큰을 통해 유저정보가져오기
		String username = userDetails.getUsername();
		if(username.equals(id)) { //토큰 같은지 확인
			return new ResponseEntity<>(userService.updateUserPassword(id, userReq), HttpStatus.OK);
		}
		return ResponseEntity.status(401).body(BaseResponseBody.of(401, "fail"));
	}
}

