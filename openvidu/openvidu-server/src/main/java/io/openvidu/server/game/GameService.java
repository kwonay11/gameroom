package io.openvidu.server.game;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.openvidu.client.internal.ProtocolElements;
import io.openvidu.server.core.Participant;
import io.openvidu.server.rpc.RpcNotificationService;
import netscape.javascript.JSObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
public class GameService {
    static final int PREPAREGAME = 0;
    static final int SELECTGAME = 1;
    static final int STARTGAME = 2;
    static final int FINISHGAME = 3;
    static final int COMPLETEPENALTY = 4;
    static final int VOTELIAR = 5;
    static final int UPDOWN = 1;
    static final int INITIAL = 2;
    static final int LIAR = 3;
    static final int SMILE = 4;
    static final int DRUNKTEST = 5;

    private static final Logger log = LoggerFactory.getLogger(GameService.class);

    static RpcNotificationService rpcNotificationService;
    public void controlGame(Participant participant, JsonObject message, Set<Participant> participants, RpcNotificationService rnfs) {
        rpcNotificationService = rnfs;
        System.out.println(participant);
        System.out.println(message);
        System.out.println(participants);
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
            case PREPAREGAME: // 게임 준비
                prepareGame(participant, message, participants, params, data);
                return;
//            case SELECTGAME: // 게임 선택
//                selectGame(participant, message, participants, params, data);
//                return;
//            case STARTGAME: // 게임 진행
//                startGame(participant, message, participants, params, data, rnfs);
//                return;
//            case FINISHGAME: // 게임 종료
//                finishGame(participant, message, participants, params, data);
//                return;
//            case COMPLETEPENALTY: // 벌칙 종료
//                completePenalty(participant, message, participants, params, data);
//                return;
//            case VOTELIAR:
//                voteLiar(participant, message, participants, params, data);
//                return;
        }
    }
    private void prepareGame(Participant participant, JsonObject message, Set<Participant> participants,
                             JsonObject params, JsonObject data) {
        System.out.println("sadhhkjhfjkasdhfjkashfjkdhafjkadhfk");
        log.info("PrepareGame is called by {}", participant.getParticipantPublicId());
        System.out.println(data);

        params.add("data", data);
//        params.add("test",);
        // 브로드 캐스팅
        for (Participant p : participants) {
            rpcNotificationService.sendNotification(p.getParticipantPrivateId(),
                    ProtocolElements.PARTICIPANTSENDMESSAGE_METHOD, params);
        }
    }
}


