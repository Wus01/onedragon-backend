package restapi.prac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restapi.prac.model.StoreDO;
import restapi.prac.repository.StoreRepository;

import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    @Autowired // 생성자 주입
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public StoreDO createStore(StoreDO storeDO) {
        // storeDO 객체로 저장
        return storeRepository.save(storeDO);
    }

    public List<StoreDO> createStoreList(List<StoreDO> storeList) {
        // storeDO 객체 리스트로 저장
        return storeRepository.saveAll(storeList);
    }
}
