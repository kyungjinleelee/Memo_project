package com.teamsparta.crud.repository;

import com.teamsparta.crud.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Repository 없어도 무방함 > 트랜잭셔널에서 구현체를 자동으로 만들어주고, bean으로 만들어 주기 때문
@Repository // 빈 객체로 등록 (@Component와 동일)(스프링이 run 될 때 IOC Container에 이 클래스를 빈으로 만들어서 등록해줌)
public interface MemoRepository extends JpaRepository<Memo, Long> {

    List<Memo> findAllByOrderByModifiedAtDesc();    // 수정된날짜기준 내림차순해!
//  List<Memo> findAllByUsername(String username);  // select * from memo where username = '이거' 와 동일
    List<Memo> findAllByContentsContainsOrderByModifiedAtDesc(String keyword);
    // ==> 이렇게 query method 이용하면, 동적으로 편하게 sql 처리 가능!!!

}