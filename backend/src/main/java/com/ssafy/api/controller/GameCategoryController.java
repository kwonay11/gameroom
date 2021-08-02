package com.ssafy.api.controller;
import com.ssafy.api.service.GameCategoryService;
import com.ssafy.db.entity.*;
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

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "게임 API", tags = {"Games"})
@RestController
@RequestMapping("/games")
public class GameCategoryController {

    @Autowired
    GameCategoryService gameService;

    @GetMapping("/")
    @ApiOperation(value = "게임 이름과 설명 조회", notes = "게임 이름과 게임 설명을 response함")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<GameCategory>> getGameSummary() {
        /**
         * game_category 테이블에서 게임 이름과 게임 설명을 조회하여 response 해줌
         */
        List<GameCategory> res = gameService.getGameCategoryList();

        return ResponseEntity.status(200).body(res);
    }
}
