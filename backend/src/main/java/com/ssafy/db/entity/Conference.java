package com.ssafy.db.entity;


import lombok.*;
import org.graalvm.compiler.lir.LIRInstruction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity //해당 클래스가 엔티티를 위한 크래스이며, 해당 클래스의 인스턴스들이 jpa로 관리되는 엔티티 객체라는 것을 의미
@Getter //Getter 메서드 생성
//@Setter
@Builder // 객체 생성 처리
@AllArgsConstructor // @Builder를 사용하기 위한 어노테이션
@NoArgsConstructor // @Builder를 사용하기 위한 어노테이션
@ToString(exclude = {"owner", "gameCategory"})
public class Conference extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User owner;  //유저

    @ManyToOne(fetch = FetchType.LAZY)
    private GameCategory gameCategory; // 게임 카테고리

    private LocalDateTime callStartTime; //방 생성시간

    private LocalDateTime callEndTime; //방 종료시간

    private String title; // 방제목
    @Builder.Default
    private boolean isActive = true; //방 활성화여부(default = true)

    private String password; // 비밀방사용시 비밀번호 설정용 .... 물어볼 것 암호화처리해야되요?

    private int maxUser; // 최대인원


}
