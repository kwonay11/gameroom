package com.ssafy.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameStatusRes {
    String keyword;
    int questioner;
    int round;
    Long gameStatus;
}
