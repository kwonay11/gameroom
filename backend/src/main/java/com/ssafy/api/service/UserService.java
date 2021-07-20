package com.ssafy.api.service;

import com.ssafy.api.request.UserDTO;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface UserService {
	User createUser(UserRegisterPostReq userRegisterInfo);
	User getUserByUserId(String userId);

	void removeUser(String userId); //user 삭제

	void modifyUser(UserDTO userDTO);

	default User dtoToEntity(UserDTO userDTO) {
		User user = User.builder()
				.userId(userDTO.getUserId())
				.nickName(userDTO.getNickName())
				.exp(userDTO.getExp())
				.build();
		return user;
	}

	default UserDTO EntityToUser(User user) {

		UserDTO userDTO = UserDTO.builder()
				.userId(user.getUserId())
				.id(user.getId())
				.nickName(user.getNickName())
				.exp(user.getExp())
				.build();

		return userDTO;
	}

}
