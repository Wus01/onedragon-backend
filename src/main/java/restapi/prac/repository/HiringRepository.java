package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restapi.prac.model.HiringBoard;

import java.util.Optional;

@Repository
public interface HiringRepository extends JpaRepository<HiringBoard, Long> {

    // JPQL을 사용하여 HiringBoard를 조회할 때 StoreInfo를 즉시 함께(EAGERLY) 가져옵니다.
    @Query("SELECT h FROM HiringBoard h JOIN FETCH h.storeInfo WHERE h.id = :id")
    Optional<HiringBoard> findByIdWithStoreInfo(@Param("id") Long id);

    @Query("SELECT h FROM HiringBoard h " +
            "JOIN FETCH h.storeInfo " + // 기존 상점 정보
            "LEFT JOIN FETCH h.applyList al " + // 지원 정보 (지원자가 없을 수도 있으니 LEFT JOIN 추천)
            "LEFT JOIN FETCH al.userInfo " + // 지원한 유저 정보
            "WHERE h.hiringNo = :hiringNo")
    Optional<HiringBoard> findDetailWithApplyAndUser(@Param("hiringNo") Long hiringNo);

}
