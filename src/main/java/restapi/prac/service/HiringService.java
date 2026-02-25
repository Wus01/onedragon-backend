package restapi.prac.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.entity.HiringBoardEntity;
import restapi.prac.repository.HiringRepository;

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

    @Transactional
    public void confirmHiring(Long id) {
        // 1. 첫 번째 업데이트 (공고 상태 변경)
        int result1 = hiringRepository.updateStatusHiringBoard(id);

        // 2. 두 번째 업데이트 (매장 활동 시간 갱신 등)
        int result2 = hiringRepository.updateStatusApplyInfo(id);

        if (result1 == 0 || result2 == 0) {
            throw new RuntimeException("업데이트 대상이 존재하지 않습니다.");
        }
    }

}
