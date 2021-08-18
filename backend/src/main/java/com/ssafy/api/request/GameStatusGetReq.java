package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("GameStatusGetReq")
public class GameStatusGetReq {
    Integer status;  // 0: 게임 시작, 1: 진행 중, 2: 종료, 3: 게임 카테고리 변경
    Long category;
    Integer round;
    Long conference;
    int mainstream_idx;
}
