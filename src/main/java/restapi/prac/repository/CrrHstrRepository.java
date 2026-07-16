package restapi.prac.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import restapi.prac.model.CrrHstrId;
import restapi.prac.model.dto.request.CrrHstrRequestDto;
import restapi.prac.model.entity.CrrHstrEntity;
import restapi.prac.model.entity.HiringBoardEntity;

import java.util.Optional;

@Repository
public interface CrrHstrRepository extends JpaRepository<CrrHstrEntity, Long> {

        // FETCH JOIN을 사용하여 StoreEntity(지점 정보)를 한 번에 쿼리합니다.
        @Query("SELECT c FROM CrrHstrEntity c " +
                "JOIN FETCH c.storeInfo " + // 엔티티에 선언된 변수명 storeInfo 사용
                "WHERE c.delYn = false AND c.userId = :userId AND c.storeId = :storeId")
        Optional<CrrHstrEntity> findByIdWithStore(@Param("userId") String userId,
                                                  @Param("storeId") Integer storeId);

        @Query("SELECT c FROM CrrHstrEntity c " +
                "JOIN FETCH c.storeInfo " + // 기존 상점 정보
                "WHERE c.storeId = :storeId")
        Optional<CrrHstrEntity> selectMyCrrHstrList(@org.springframework.data.repository.query.Param("userId") String userId);

        @Query("SELECT c FROM CrrHstrEntity c " +
                "JOIN FETCH c.storeInfo " +
                "WHERE c.crrHstrNo = :crrHstrNo")
        Optional<CrrHstrEntity> selectOneCrrHstr(@org.springframework.data.repository.query.Param("crrHstrNo") Long crrHstrNo);

}
 