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
    
    // 지점 검색 list 가져오기
    public Page<StoreEntity> searchStoreByName(String nm, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);

        // 💡 검색어가 없으면 findAll, 있으면 검색 조회
        if (nm == null || nm.trim().isEmpty()) {
            return storeRepository.findAll(pageable);
        } else {
            return storeRepository.findByStoreNmContaining(nm, pageable);
        }
    }


}
