package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 모델 간 공통 사항 정의.
 */
@Getter
//@Setter
@MappedSuperclass
public class BaseEntity {
    @Id //@Entity가 붙은 클래스는 pk필드를 @id로 지정해줘야한다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동생성되기 위한 어노테이션
    Long id = null;
}
