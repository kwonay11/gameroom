package io.openvidu.server.game;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GameService {
    static final int PREPAREGAME = 0; // 게임 시작
    static final int STARTGAME = 1; //게임중
    static final int FINISHGAME = 2; // 게임종료
    static final int SELECTGAME = 3; // 게임변경

    //게임종류
    static final int BODYTALK = 1; // 몸으로 말해요
    static final int CILENCETALK = 3; // 고요속의외침
    static final int INITIAL = 2;
    static final int LIAR = 3;
    static final int SMILE = 4;
    static final int DRUNKTEST = 5;

    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    static RpcNotificationService rpcNotificationService;

    public void controlGame(Participant participant, JsonObject message, Set<Participant> participants, RpcNotificationService rnfs) {
        rpcNotificationService = rnfs;
        System.out.println("controlgame 시작");
        System.out.println(participant); // [participantPrivateId=7ld50k6qfslg5ao22malv3lve8, participantPublicId=con_HxGJQoxLm8, streaming=true]
        System.out.println(message); // {"to":[],"data":"{\"gameStatus\":0,\"category\":1,\"round\":0,\"conferencid\":\"15\"}","type":"signal:game"}
        System.out.println(participants); //[[participantPrivateId=hp7f9t6at49glgiv6ncgaa0t83, participantPublicId=con_CTrBt4LyO2, streaming=true],
        // [participantPrivateId=7ld50k6qfslg5ao22malv3lve8, participantPublicId=con_HxGJQoxLm8, streaming=true]]

        JsonObject params = new JsonObject();

        // 요청 보낸 사람 ID 저장
        if (participant != null) {
            params.addProperty(ProtocolElements.PARTICIPANTSENDMESSAGE_FROM_PARAM,
                    participant.getParticipantPublicId());
        }

        // 타입 저장
        if (message.has("type")) {
            params.addProperty(ProtocolElements.PARTICIPANTSENDMESSAGE_TYPE_PARAM, message.get("type").getAsString());
        }
        // data 파싱
        String dataString = message.get("data").getAsString();
        JsonObject data = (JsonObject) JsonParser.parseString(dataString);

        // 게임 상태에 따라 분기
        int gameStatus = data.get("gameStatus").getAsInt();

        // 게임 상태 추가, 벌칙 완료일때만 4 -> 0 으로
        //data.addProperty("gameStatus", Integer.toString(gameStatus));

        switch (gameStatus) {
            case PREPAREGAME: // 게임 시작
                prepareGame(participant, message, participants, params, data, rnfs);
                return;
            case STARTGAME: // 게임 진행
                startGame(participant, message, participants, params, data, rnfs);
                return;
            case FINISHGAME: // 게임 종료
                finishGame(participant, message, participants, params, data);
                return;
            case SELECTGAME: // 게임 선택
                selectGame(participant, message, participants, params, data);
                return;


        }
    }

    private void prepareGame(Participant participant, JsonObject message, Set<Participant> participants,
                             JsonObject params, JsonObject data, RpcNotificationService rnfs) {
        System.out.println("게임 시작 ");
        log.info("PrepareGame is called by {}", participant.getParticipantPublicId());
        System.out.println(data);


        int category = data.get("category").getAsInt();
        int round = data.get("round").getAsInt();
        int conferenceId = data.get("conferenceId").getAsInt();

        System.out.println("데이터 체크 ");
        System.out.println(category + " " + round + " " + conferenceId);
        String apiUrl = "http://localhost:8080/api-boot/games/play";

//        switch (category) {
//            case BODYTALK | CILENCETALK: //  몸으로 말해요 or 고요속의 외침
        UriComponentsBuilder builder  = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("status",0)
                .queryParam("category",category)
                .queryParam("round",round)
                .queryParam("conference",conferenceId);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();


        RestTemplate restTpl = new RestTemplate(httpRequestFactory);
        HttpHeaders headers = new HttpHeaders(); // 담아줄 header
        HttpEntity entity = new HttpEntity<>(headers); // http entity에 header 담아줌
        ResponseEntity<String> response  = restTpl.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);


        System.out.println("return 확인");
        System.out.println(response);
        try {
            JSONParser jsonParsers = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParsers.parse(response.getBody());

            data.addProperty("data", String.valueOf(jsonObject));
            params.add("data", data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//                break;
//
//        }
        for (Participant p : participants) {
            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }
    }


    // game 진행
    private void startGame(Participant participant, JsonObject message, Set<Participant> participants,
                           JsonObject params, JsonObject data, RpcNotificationService rnfs) {
        log.info("startGame is called by {}", participant.getParticipantPublicId());

        int category = data.get("category").getAsInt();
        String cat = data.get("category").getAsString();
        String round = data.get("round").getAsString();
        int conferenceId = data.get("conferenceId").getAsInt();
        String JWT = "Bearer " + data.get("JWT").getAsString();
        String apiUrl = "http://localhost:8080/api-boot/games/play";
//        switch (category) {
//            case BODYTALK | CILENCETALK : //  몸으로 말해요 고요속의 외침
        UriComponentsBuilder builder  = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("status",1)
                .queryParam("category",category)
                .queryParam("conference",conferenceId)
                .queryParam("round",round);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();


        RestTemplate restTpl = new RestTemplate(httpRequestFactory);
        HttpHeaders headers = new HttpHeaders(); // 담아줄 header
        headers.add("Authorization", JWT);
        HttpEntity entity = new HttpEntity<>(headers); // http entity에 header 담아줌
        ResponseEntity<String> response  = restTpl.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        System.out.println("return 확인");
        System.out.println(response);
        try {
            JSONParser jsonParsers = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParsers.parse(response.getBody());

            data.addProperty("data", String.valueOf(jsonObject));
            params.add("data", data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//                break;
//        }
        for (Participant p : participants) {
            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }

    }

    private void finishGame(Participant participant, JsonObject message, Set<Participant> participants,
                            JsonObject params, JsonObject data) {
        log.info("finishGame is called by {}", participant.getParticipantPublicId());

        int category = data.get("category").getAsInt();
        int conferenceId = data.get("conferenceId").getAsInt();
        int round = data.get("round").getAsInt();
        String JWT = "Bearer " + data.get("JWT").getAsString();

        String apiUrl = "http://localhost:8080/api-boot/games/play";
//        switch (category) {
//            case BODYTALK  | CILENCETALK: //  몸으로 말해요 or 고요속의 외침
        UriComponentsBuilder builder  = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("status",2)
                .queryParam("category",category)
                .queryParam("conference",conferenceId)
                .queryParam("round",round);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();


        RestTemplate restTpl = new RestTemplate(httpRequestFactory);
        HttpHeaders headers = new HttpHeaders(); // 담아줄 header
        headers.add("Authorization", JWT);
        HttpEntity entity = new HttpEntity<>(headers); // http entity에 header 담아줌
        System.out.println("확인");
        ResponseEntity<String> response  = restTpl.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);





        System.out.println("return 확인");
        System.out.println(response);
        try {
            JSONParser jsonParsers = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParsers.parse(response.getBody());


            data.addProperty("data", String.valueOf(jsonObject));
            params.add("data", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
//                break;
//        }
        for (Participant p : participants) {
            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }

    }

    private void selectGame(Participant participant, JsonObject message, Set<Participant> participants,
                            JsonObject params, JsonObject data) {
        log.info("selectGame is called by {}", participant.getParticipantPublicId());
        int category = data.get("category").getAsInt();
        int conferenceId = data.get("conferenceId").getAsInt();
        String cat = data.get("category").getAsString();

        String apiUrl = "http://localhost:8080/api-boot/games/play";

        UriComponentsBuilder builder  = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("status",3)
                .queryParam("conference",conferenceId)
                .queryParam("category",category);
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();


        RestTemplate restTpl = new RestTemplate(httpRequestFactory);
        HttpHeaders headers = new HttpHeaders(); // 담아줄 header
        HttpEntity entity = new HttpEntity<>(headers); // http entity에 header 담아줌
        ResponseEntity<String> response  = restTpl.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);

        try {
            JSONParser jsonParsers = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParsers.parse(response.getBody());
            System.out.println(jsonObject);

            data.addProperty("data", String.valueOf(jsonObject));
//            data.addProperty("data", category);
            params.add("data", data);
        } catch (ParseException e) {
            e.printStackTrace();
        }



        for (Participant p : participants) {
            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }
    }


}



