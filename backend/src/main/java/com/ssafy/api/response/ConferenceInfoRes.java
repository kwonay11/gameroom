package com.ssafy.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConferenceInfoRes {
    long id;
    String ownerNickname;
    String title;
    String gameName;
    long maxUser;
    long nowUser;
}
