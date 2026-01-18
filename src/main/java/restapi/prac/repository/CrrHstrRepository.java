package restapi.prac.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restapi.prac.model.CrrHstrId;
import restapi.prac.model.entity.CrrHstrEntity;

import java.util.Optional;

@Repository
public interface CrrHstrRepository extends JpaRepository<CrrHstrEntity, CrrHstrId> {

        // FETCH JOIN을 사용하여 StoreEntity(지점 정보)를 한 번에 쿼리합니다.
        @Query("SELECT c FROM CrrHstrEntity c " +
                "JOIN FETCH c.storeInfo " + // 엔티티에 선언된 변수명 storeInfo 사용
                "WHERE c.delYn = false AND c.userId = :userId AND c.storeId = :storeId")
        Optional<CrrHstrEntity> findByIdWithStore(@Param("userId") String userId,
                                                  @Param("storeId") Integer storeId);

}
