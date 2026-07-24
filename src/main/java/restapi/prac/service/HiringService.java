package restapi.prac.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.entity.HiringBoardEntity;
import restapi.prac.repository.HiringRepository;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class HiringService {

    private final HiringRepository hiringRepository;

    public HiringService(HiringRepository hiringRepository) {
        this.hiringRepository = hiringRepository;
    }

    // 전체조회
//    public Page<HiringBoardEntity> getHirings(Pageable pageable) {

    /// /        return hiringRepository.findAll(pageable);
//        return hiringRepository.findAllByOrderByRgstDateDesc(pageable);
//
//    }
    public Page<Object[]> findAllWithCodeName(Pageable pageable) {
        return hiringRepository.findAllWithCodeName(pageable);
    }
    //상세조회
//    public Optional<HiringBoardEntity> getPost(Long id){
//            return hiringRepository.findByIdWithStoreInfo(id);
//}
    @Transactional(readOnly = true)
    public Optional<HiringBoardDTO> getPost(Long id){
        List<Object[]> resultList = hiringRepository.findByIdWithStoreInfo(id);

        if(resultList.isEmpty()){
            return Optional.empty();
        }
        // 리스트 0번째의 찐 데이터 로우 꺼내기
        Object[] row = resultList.get(0);

        HiringBoardEntity entity = (HiringBoardEntity) row[0];
        String dtlCdNm = (String) row[1];

        return Optional.of(new HiringBoardDTO(entity, dtlCdNm));
    }

//    @Transactional
////    public void confirmHiring(@Param("userId") String userId, @Param("applyNos") List<Long> applyNos, @Param("hiringNo") Long hiringNo) {
//    public void confirmHiring(HiringBoardDTO hiringBoardDTO) {
//        String userId = hiringBoardDTO.getUserId();
//        Long hiringNo = hiringBoardDTO.getHiringNo();
//        List<Long> applyNos = hiringBoardDTO.getApplyNos();
//        String hiringSts = hiringBoardDTO.getHiringSts();
//        String applySts = hiringBoardDTO.getApplySts();
//
//        // 1. 첫 번째 업데이트 (공고 상태 변경)
//        int result1 = hiringRepository.updateStatusHiringBoard(userId, hiringNo); //id만
//
//        // 2. 두 번째 업데이트 (apply_info)
//        int result2 = hiringRepository.updateStatusApplyInfo(userId, applyNos, hiringNo, applySts); //id, hiringNo
//
//        if (result1 == 0 || result2 == 0) {
//            throw new RuntimeException("업데이트 대상이 존재하지 않습니다.");
//        }
//    }

    // 공고 등록
    public HiringBoardEntity createHiring(HiringBoardEntity hiringEntity) {
        // hiringBoardEntity 객체로 저장
        return hiringRepository.save(hiringEntity);
    }

}
