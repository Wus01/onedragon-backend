package restapi.prac.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.entity.CrrHstrEntity;
import restapi.prac.model.dto.request.CrrHstrRequestDto;
import restapi.prac.model.dto.response.CrrHstrResponseDto;
import restapi.prac.repository.CrrHstrRepository;
import java.util.Optional;

@Service
public class CrrHstrService {

    private final CrrHstrRepository crrHstrRepository;

    public CrrHstrService(CrrHstrRepository crrHstrRepository){
        this.crrHstrRepository = crrHstrRepository;
    }

    // SELECT 1
    public Optional<CrrHstrEntity> getCrrHstr(String userId , Integer storeId){
        return crrHstrRepository.findByIdWithStore(userId, storeId);
    }

    // UPDATE
    @Transactional
    public CrrHstrResponseDto updateCrrHstr(String userId, Integer storeId, CrrHstrRequestDto dto) {
        CrrHstrEntity entity = crrHstrRepository.findByIdWithStore(userId, storeId)
                .orElseThrow(() -> new RuntimeException("데이터 없음"));

        // 리액트 localStorage에서 넘어온 그 ID를 그대로 저장!
        entity.setUpdtId(dto.getUserId());

        entity.setCrrStrtDate(dto.getCrrStrtDate());
        entity.setCrrEndDate(dto.getCrrEndDate());

        return CrrHstrResponseDto.from(entity);
    }

    // DELETE
    @Transactional
    public CrrHstrResponseDto deleteCrrHstr(String userId, Integer storeId, CrrHstrRequestDto dto) {
        // 1. 기존 데이터 조회
        CrrHstrEntity entity = crrHstrRepository.findByIdWithStore(userId, storeId)
                .orElseThrow(() -> new RuntimeException("삭제할 데이터를 찾을 수 없습니다."));

        // 2. 논리 삭제 처리 (Soft Delete)
        entity.setDelYn(true); // 또는 "Y" (DB 타입에 맞춰 설정)

        // 3. 수정자 정보 업데이트 (리액트 로컬스토리지에서 넘어온 ID 사용)
        entity.setUpdtId(dto.getUserId());

        // 4. Dirty Checking에 의해 트랜잭션 종료 시 자동으로 Update 쿼리가 실행됨
        return CrrHstrResponseDto.from(entity);
    }

    // INSERT
    @Transactional
    public CrrHstrResponseDto createCrrHstr(CrrHstrRequestDto dto) {
        // 1. 엔티티 생성
        CrrHstrEntity entity = new CrrHstrEntity();

        // 2. DTO 값 매핑
        entity.setUserId(dto.getUserId());
        entity.setStoreId(dto.getStoreId());
        entity.setCrrStrtDate(dto.getCrrStrtDate());
        entity.setCrrEndDate(dto.getCrrEndDate());

        // Boolean 타입 처리 (Lombok getAuthYn() 사용)
        entity.setAuthYn(dto.getAuthYn() != null ? dto.getAuthYn() : false);
        entity.setDelYn(false);

        // 3. 수동 Auditing (리액트가 보낸 userId 사용)
        entity.setRgstId(dto.getUserId());
        entity.setUpdtId(dto.getUserId());

        // 4. 저장
        CrrHstrEntity savedEntity = crrHstrRepository.save(entity);
        return CrrHstrResponseDto.from(savedEntity);
    }



}
