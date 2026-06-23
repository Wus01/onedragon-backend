package restapi.prac.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.entity.HiringBoardEntity;
import restapi.prac.repository.HiringRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HiringService {

    private final HiringRepository hiringRepository;
    public HiringService(HiringRepository hiringRepository){
        this.hiringRepository = hiringRepository;
    }

    // 전체조회
    public Page<HiringBoardEntity> getHirings(Pageable pageable) {
        return hiringRepository.findAll(pageable);
    }
    //상세조회
    public Optional<HiringBoardEntity> getPost(Long id){

        return hiringRepository.findByIdWithStoreInfo(id);
    }

    // repository 수정 후 아래 코드 수정 plz.
    // 오류나서 일단 주석처리함.
    @Transactional
    public void confirmHiring(@Param("userId") String userId, @Param("applyNos") List<Long> applyNos, @Param("hiringNo") Long hiringNo) {
        // 1. 첫 번째 업데이트 (공고 상태 변경)
        int result1 = hiringRepository.updateStatusHiringBoard(userId, hiringNo); //id만

        // 2. 두 번째 업데이트 (매장 활동 시간 갱신 등)
        int result2 = hiringRepository.updateStatusApplyInfo(userId, applyNos, hiringNo); //id, hiringNo

        if (result1 == 0 || result2 == 0) {
            throw new RuntimeException("업데이트 대상이 존재하지 않습니다.");
        }
    }

    // 공고 등록
    public HiringBoardEntity createHiring(HiringBoardEntity hiringEntity) {
        // hiringBoardEntity 객체로 저장
        return hiringRepository.save(hiringEntity);
    }

}
