package com.ssafy.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GameRoomInfoRes {
    String title;
    long gameId;
    String gameName;
    String gameSummary;
    String ownerNicknames;
    long maxUser;
}
