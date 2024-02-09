package com.teamsparta.crud.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass       // 하단의 추상클래스에 선언한 멤버 변수를 Column으로 인식해줌(createdAt, modifiedAt)
@EntityListeners(AuditingEntityListener.class)  // 이거 달아줘야 자동으로 시간 넣어주는 기능 수행됨
public abstract class Timestamped { // 이 클래스 자체를 객체로 사용할 일은 없기에 abstract 걸어주는 게 더 좋음 (없어도 동작엔 무방함)

    @CreatedDate        // Entity 객체 생성되서 저장될 때, createdAt가 자동저장됨
    @Column(updatable = false)  // 이후에 최초 작성시간 수정 안되게끔 업뎃 막아줌
    @Temporal(TemporalType.TIMESTAMP)   // java의 날짜 데이터를 매핑할 때 사용
    private LocalDateTime createdAt;

    @LastModifiedDate   // 시간 변경될 때마다 자동으로 저장
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;
}