package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ApiModel("ConferenceRegisterPostReq")
public class ConferenceRegisterPostReq {

    private String id;

    private String userid;

    private Long gamecategory;

    private String title;

    private String password;

    private int maxUser;

    private LocalDateTime callStartTime;
}
