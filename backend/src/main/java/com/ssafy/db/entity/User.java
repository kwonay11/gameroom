package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import javax.persistence.Entity;

/**
 * 유저 모델 정의.
 */
@Entity //해당 클래스가 엔티티를 위한 크래스이며, 해당 클래스의 인스턴스들이 jpa로 관리되는 엔티티 객체라는 것을 의미
@Getter //Getter 메서드 생성
@Setter
@Builder // 객체 생성 처리
@AllArgsConstructor // @Builder를 사용하기 위한 어노테이션
@NoArgsConstructor // @Builder를 사용하기 위한 어노테이션
@ToString
public class User extends BaseEntity{


    private String userId; // 로그인용 유저 아이디
    private String nickname; // 방에서 사용할 닉네임
    @Builder.Default
    private int exp = 0; // 경험치

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //json 표시 안되게 하는 어노테이션
    private String password;



}
