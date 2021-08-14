package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("ConferencePasswordGetReq")
public class ConferencePasswordGetReq {
    String password;
}
