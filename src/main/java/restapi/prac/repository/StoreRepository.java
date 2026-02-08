package restapi.prac.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.entity.StoreEntity;

@Repository
public interface StoreRepository  extends JpaRepository<StoreEntity, Long> {
    // 지점 검색 list 조회
    Page<StoreEntity> findByStoreNmContaining(String storeNm, Pageable pageable);


}
