package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class testres extends BaseResponseBody {

    private String keyword;
    private String questioner;
    private String round;
}
