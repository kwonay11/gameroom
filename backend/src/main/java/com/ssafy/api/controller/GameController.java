package com.ssafy.api.controller;

import com.ssafy.api.request.GameStatusGetReq;
import com.ssafy.api.response.*;
import com.ssafy.api.service.*;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;
import java.util.*;

@Api(value = "게임 API", tags = {"Game"})

@RestController
@RequestMapping("/api-boot/games")
public class GameController {

    @Autowired
    GameService gameService;
    @Autowired
    UserService userService;
    @Autowired
    UserConferenceService userConferenceService;
    @Autowired
    UserGameService userGameService;
    @Autowired
    ConferenceService conferenceService;
   

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
            @ApiResponse(code = 404, message = "유효하지 않은 gameStatusReq"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getGameStatus(@ApiIgnore Authentication authentication,
                                           GameStatusGetReq gameStatusGetReq) {
        System.out.println(gameStatusGetReq);
        if (gameStatusGetReq.getStatus() == null) {
            System.out.println("걸리는지 확인");
            return ResponseEntity.ok(ConferenceRes.of(404, "fail"));
        }

        if (gameStatusGetReq.getStatus() == 0) {  // 0: 게임 시작
            // game db 저장
            Game game = gameService.saveGame(gameStatusGetReq.getConference(), gameStatusGetReq.getCategory());

            // conference에 참가 중인 user 목록을 가져옴
            List<UserConference> userConferenceList = userConferenceService.getUserConferenceByConferenceId(gameStatusGetReq.getConference());

            // db에 플레이어 별 user_game, game_history 저장
            for (UserConference userConference : userConferenceList) {
                userGameService.saveUserGame(userConference.getUser(), game);
                gameService.saveGameHistory(userConference.getUser(), game, 0, 0);
            }
            return makeGameThings(gameStatusGetReq, userConferenceList, game);
        } else if (gameStatusGetReq.getStatus() == 1) {  // 1: 진행 중
            // 정답 맞춘 플레이어 확인을 위한 토큰
            SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();

            // conference에 참가 중인 user 목록을 가져옴
            List<UserConference> userConferenceList = userConferenceService.getUserConferenceByConferenceId(gameStatusGetReq.getConference());

            // db에 플레이어 별 game_history 저장
            Game game = userGameService.getUserGameByUser(userDetails.getUser()).get().getGame();
            for (UserConference userConference : userConferenceList) {
                User user = userConference.getUser();
                if (user.getId() == userDetails.getUser().getId())
                    gameService.saveGameHistory(user, game, 1, 1);
                else
                    gameService.saveGameHistory(user, game, 1, 0);
            }
            return makeGameThings(gameStatusGetReq, userConferenceList, game);
        } else if (gameStatusGetReq.getStatus() == 2) {  // 2: 종료
            // 마지막 정답 맞춘 플레이어 확인을 위한 토큰
            SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();

            // conference에 참가 중인 user 목록을 가져옴
            List<UserConference> userConferenceList = userConferenceService.getUserConferenceByConferenceId(gameStatusGetReq.getConference());

            // db에 플레이어 별 game_history 저장, user_game 삭제
            Game game = userGameService.getUserGameByUser(userDetails.getUser()).get().getGame();
            for (UserConference userConference : userConferenceList) {
                User user = userConference.getUser();
                if (user.getId() == userDetails.getUser().getId())
                    gameService.saveGameHistory(user, game, 2, 1);
                else
                    gameService.saveGameHistory(user, game, 2, 0);
                userGameService.deleteUserGame(user, game);
            }

            // 플레이어 별 맞춘 문제 수 구하기
            HashMap<Long, Integer> answerMap = new HashMap<>();
            List<GameHistory> gameHistoryList = gameService.getGameHistoryListByGameAndRanking(game, 1);
            for (GameHistory gameHistory : gameHistoryList) {
                Long key = gameHistory.getUser().getId();
                if (answerMap.containsKey(key))
                    answerMap.put(key, answerMap.get(key) + 1);
                else
                    answerMap.put(key, 1);
            }
            List<UserAnswerSet> userAnswerList = new LinkedList<>();
            for (Long key : answerMap.keySet()) {
                User user = userService.getUserById(key);
                userAnswerList.add(new UserAnswerSet(user.getId(), user.getNickname(), answerMap.get(key)));
            }
            Collections.sort(userAnswerList, Comparator.comparingInt(UserAnswerSet::getAnswer).reversed());

            // 플레이어 별 win_rate 테이블 갱신, user_game 테이블 데이터 삭제
            Long firstRankedPlayer = userAnswerList.get(0).getId();
            for (UserConference userConference : userConferenceList) {
                User user = userConference.getUser();
                WinRate winRate = userService.getWinRateByUserAndGameCategory(user, gameService.getGameCategoryById(gameStatusGetReq.getCategory()));
                if (winRate == null) {
                    winRate = WinRate.builder().user(user).gameCategory(gameService.getGameCategoryById(gameStatusGetReq.getCategory())).firstRanked(0).gameCount(0).build();
                }
                if (user.getId() == firstRankedPlayer)
                    winRate.setFirstRanked(winRate.getFirstRanked() + 1);
                winRate.setGameCount(winRate.getGameCount() + 1);
                userService.saveWinRate(winRate);
                //작동안함 userGameService.deleteUserGame(user, game);
            }

            // game 테이블 종료 시간
            game.setGameEndTime(LocalDateTime.now());
            gameService.saveGame(game);

            JSONObject obj = new JSONObject();
            obj.put("data", userAnswerList);


            return ResponseEntity.status(200).body(obj);
        } else if (gameStatusGetReq.getStatus() == 3) {  // 3: 게임 카테고리 변경
            // conference 테이블 game_category 변경
            Conference conference = conferenceService.getConferenceById(gameStatusGetReq.getConference()).get();
            conference.setGameCategory(gameService.getGameCategoryById(gameStatusGetReq.getCategory()));
            Conference result = conferenceService.saveConference(conference);


            GameChagneRes res = GameChagneRes.builder().category(result.getGameCategory().getId()).gameStatus(3L).round(0L).build();
            return ResponseEntity.status(200).body(res);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(404, "invalid Parameters"));
    }

    private ResponseEntity<?> makeGameThings(GameStatusGetReq gameStatusGetReq, List<UserConference> userConferenceList, Game game) {
        // keyword 랜덤 선택
        String keyword = gameService.getKeywordRand(gameStatusGetReq.getCategory());

        if (gameStatusGetReq.getCategory() == 1 || gameStatusGetReq.getCategory() == 2) {  // 몸으로 말해요 || 고요속의 외침
//            int questioner = -1;
            if (gameStatusGetReq.getStatus() == 0) {
                // 출제자 랜덤 선택
                Random rand = new Random();
                int questioner = rand.nextInt(userConferenceList.size());
                GameStatusRes res = GameStatusRes.builder().keyword(keyword).questioner(questioner).round(gameStatusGetReq.getRound() + 1).build();
                return ResponseEntity.status(200).body(res);
            } else {
                // 출제자 받은그대로 보내기
                GameStatusRes res = GameStatusRes.builder().keyword(keyword).questioner(gameStatusGetReq.getMainstream_idx()).round(gameStatusGetReq.getRound() + 1).build();
                return ResponseEntity.status(200).body(res);
            }


        } else if (gameStatusGetReq.getCategory() == 4 || gameStatusGetReq.getCategory() == 5) {  // 순간 포착4 || 글자맞추기5
            String question = null;

            if (gameStatusGetReq.getCategory() == 5) {
                question = keyword.substring(0, 2) + "**";
                System.out.println("question : " + question);
            }
            GameStatusRes2 res = GameStatusRes2.builder().keyword(keyword).question(question).round(gameStatusGetReq.getRound() + 1).gameStatus(1L).build();
            return ResponseEntity.status(200).body(res);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(404, "invalid Parameters"));
    }
}


