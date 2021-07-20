package com.ssafy.api.service;

import com.ssafy.api.request.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setUserId(userRegisterInfo.getId());
		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUserByUserId(String userId) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findUserByUserId(userId).get();
		return user;
	}

	@Override
	public void removeUser(String userId) {
		// 유저 정보 삭제 (userId를 통한 삭제)
		Optional<User> user = userRepository.findByUserId(userId);
		if (user.isPresent()) {
			userRepository.delete(user.get());
		}
	}
//
	@Override
	public void modifyUser(UserDTO userDTO) {
		/* 유저 정보 수정
		* 파라미터로 들어온 userDTO에서 userId를 뽑음
		* userId를 기준으로 유저찾음
		* 유저 닉네임 변경 후 저장 */
		String userId = userDTO.getUserId();

		Optional<User> result = userRepository.findByUserId(userId);

		if (result.isPresent()) {
			User user = result.get();
			user.changeNickName(userDTO.getNickName());
			userRepository.save(user);
		}
	}
}
