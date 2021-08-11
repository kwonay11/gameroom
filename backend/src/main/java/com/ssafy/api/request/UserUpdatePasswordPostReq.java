package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("UserUpdatePasswordPostReq")
public class UserUpdatePasswordPostReq {
    @ApiModelProperty(name="현재 비밀번호", example="your_password")
    String password;
    @ApiModelProperty(name="바꿀 비밀번호" , example = "change_password")
    String changePassword;
  }
