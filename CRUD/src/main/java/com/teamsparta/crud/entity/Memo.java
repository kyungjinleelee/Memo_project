package com.teamsparta.crud.entity;

import com.teamsparta.crud.dto.MemoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // JPA가 관리할 수 있는 Entity 클래스 지정
@Getter
@Setter
@Table(name = "memo")   // 매핑할 클래스의 이름을 지정
@NoArgsConstructor  // 기본생성자 만들어주는 @
public class Memo extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment 지정
    private Long id;        // 메모장 ID
    @Column(name = "username", nullable = false)
    private String username; // 메모 작성한 사람
    @Column(name = "contents", nullable = false, length = 500)
    private String contents; // 메모 내용

    public Memo(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }

    // 업데이트
    public void update(MemoRequestDto requestDto) { // 변경할 데이터 requestDto 받아서, username이랑 contents 변경
        this.username = requestDto.getUsername();
        this.contents = requestDto.getContents();
    }
}