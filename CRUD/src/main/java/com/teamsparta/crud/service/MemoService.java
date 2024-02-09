package com.teamsparta.crud.service;

import com.teamsparta.crud.dto.MemoRequestDto;
import com.teamsparta.crud.dto.MemoResponseDto;
import com.teamsparta.crud.entity.Memo;
import com.teamsparta.crud.repository.MemoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 빈으로 등록, @Component와 동일함
public class MemoService {

    //    @Autowired  // private임에도 불구하고 Autowired 생성자로 외부에서 memoRepository 객체를 주입할 수 있다.
//    private MemoRepository memoRepository; ==> 필드 주입 방법
    private MemoRepository memoRepository;

    @Autowired  // 사실 Autowired 생략 가능한데, 생성자가 '하나만' 선언됐을 경우에 가능하다.
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }   // ==> 생성자 주입 방법 (객체의 불변성을 지켜줄 수 있기 때문에 의존성주입에서 제일 추천함)

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

        // DB 저장
        //  MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemos() {
        // DB 조회
    //  return memoRepository.findAll().stream().map(MemoResponseDto::new).toList();
        return memoRepository.findAllByOrderByModifiedAtDesc().stream().map(MemoResponseDto::new).toList();
    }   // .stream()에 의해 메모가 하나씩 빠져나오고, .map으로 변환되는데,
    // MemoResponseDto의 생성자 중 Memo를 파라미터로 가지고 있는 생성자가 호출됨 >
    // 그 뭉텅이를 List 타입으로 바꿔줌.

    // 쿼리스트링 사용
    public List<MemoResponseDto> getMemosByKeyword(String keyword){
        return memoRepository.findAllByContentsContainsOrderByModifiedAtDesc(keyword).stream().map(MemoResponseDto::new).toList();
    }
    @Transactional  // 변경 감지를 위해 @Transactional 필수!
    public Long updateMemo(Long id, MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);   // 하단에 생성해둔 findMemo 메서드 참고

        // memo 내용 수정
        memo.update(requestDto);    // Memo.java에 update라는 메서드 만들어뒀음!!

        return id;
    }

    public Long deleteMemo(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = findMemo(id);

        // memo 삭제 (지울 Entity 객체 (여기선 memo)를 파라미터 객체로 전달해준다.
        memoRepository.delete(memo);
        return id;
    }

    private Memo findMemo(Long id) {    // update와 delete 공통된 부분 따로 메서드로 빼줌
        return memoRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다.")
        );  // .orElseThrow() : 만약 memo가 null 값이 아니라면 -> memo 반환,
        // null이라면 우리가 정의한 Exception 반환
    }
}