package com.ssafy.api.request;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateNicknamePutReq {
    private Long id;
    private String userId; // 로그인용 유저 아이디
    private String nickname; // 방에서 사용할 닉네임
    private int exp; // 경험치
}
