package com.ssafy.api.controller;

import com.ssafy.api.request.GameStatusGetReq;
import com.ssafy.api.response.GameStatusRes;
import com.ssafy.api.service.GameService;
import com.ssafy.db.entity.GameCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "게임 API", tags = {"Game"})

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping()
    @ApiOperation(value = "게임 이름과 설명 조회", notes = "게임 이름과 게임 설명을 response함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<GameCategory>> getGameSummary() {
        /**
         * game_category 테이블에서 게임 이름과 게임 설명을 조회하여 response 해줌
         */
        List<GameCategory> res = gameService.getGameCategoryList();

        return ResponseEntity.status(200).body(res);
    }


    @GetMapping("/play")
    @ApiOperation(value = "게임 진행", notes = "게임이 진행되면서 필요한 데이터를 주고 받음")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getGameStatus(GameStatusGetReq gameStatusGetReq) {


        GameStatusRes res = GameStatusRes.builder()
                                        .keyword("")
                                        .questioner(0L)
                                        .round(gameStatusGetReq.getRound() + 1)
                                        .build();

        return ResponseEntity.status(200).body(res);
    }
}
