package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity //해당 클래스가 엔티티를 위한 크래스이며, 해당 클래스의 인스턴스들이 jpa로 관리되는 엔티티 객체라는 것을 의미
@Getter //Getter 메서드 생성
@Setter
@Builder // 객체 생성 처리
@AllArgsConstructor // @Builder를 사용하기 위한 어노테이션
@NoArgsConstructor // @Builder를 사용하기 위한 어노테이션
//@ToString(exclude = {"user","conference"})
public class UserConference extends BaseEntity{
    /*
    User - Conference :N:M Table
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Conference conference;



}
