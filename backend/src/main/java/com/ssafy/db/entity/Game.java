package com.ssafy.db.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity //해당 클래스가 엔티티를 위한 크래스이며, 해당 클래스의 인스턴스들이 jpa로 관리되는 엔티티 객체라는 것을 의미
@Getter //Getter 메서드 생성
@Setter
@Builder // 객체 생성 처리
@AllArgsConstructor // @Builder를 사용하기 위한 어노테이션
@NoArgsConstructor // @Builder를 사용하기 위한 어노테이션
@ToString(exclude = {"gameCategory","conference"})
@EntityListeners(AuditingEntityListener.class)
public class Game extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private GameCategory gameCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;

    @CreatedDate
    private LocalDateTime gameStartTime; // 게임 시작시간

    private LocalDateTime gameEndTime; // 게임 종료 시간

}
