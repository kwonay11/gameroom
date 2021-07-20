package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.Message;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes{
	@ApiModelProperty(name="User ID")
	String userId;
	String nickName; // 유저 닉네임 수정 후 리턴값으로 변경된 닉네임 반환을 위해 추가함
	int exp;


	public static UserRes of(User user) {
		UserRes res = new UserRes();
		res.setUserId(user.getUserId());
		res.setNickName(user.getNickName());
		res.setExp(user.getExp());
		return res;
	}

}
