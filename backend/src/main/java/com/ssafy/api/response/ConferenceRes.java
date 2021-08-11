package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel("ConferenceResponse")
public class ConferenceRes extends BaseResponseBody {
    @ApiModelProperty(name="")
    private Long roomId;

    public static ConferenceRes of(Integer statusCode, String message, Long roomId) {
        ConferenceRes res = new ConferenceRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setRoomId(roomId);
        return res;
    }
}
