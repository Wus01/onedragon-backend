package restapi.prac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.prac.model.entity.StoreEntity;
import restapi.prac.repository.StoreRepository;

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired // 생성자 주입
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // 공고 전체 조회
    public Page<StoreEntity> getStores(Pageable pageable) {
        return storeRepository.findAll(pageable);
    }

    public StoreEntity createStore(StoreEntity storeEntity) {
        // storeDO 객체로 저장
        return storeRepository.save(storeEntity);
    }

    public List<StoreEntity> createStoreList(List<StoreEntity> storeList) {
        // storeDO 객체 리스트로 저장
        return storeRepository.saveAll(storeList);
    }
    /**
     * 지점 검색 (지점명 또는 주소)
     * @param type "nm" (지점명) 또는 "addr" (주소)
     * @param keyword 검색어
     */
    public Page<StoreEntity> searchStore(String type, String keyword, int page, int size) {
        // JPA는 0페이지부터 시작하므로 page - 1 처리
        Pageable pageable = PageRequest.of(page - 1, size);

        // 1. 검색어가 없는 경우 전체 목록 반환
        if (keyword == null || keyword.trim().isEmpty()) {
            return storeRepository.findAll(pageable);
        }

        // 2. 검색 타입에 따른 분기 (type이 "addr"이면 주소 검색, 그 외엔 지점명 검색)
        if ("addr".equals(type)) {
            return storeRepository.findByStoreAddrContaining(keyword, pageable);
        } else {
            return storeRepository.findByStoreNmContaining(keyword, pageable);
        }
    }

}
