package restapi.prac.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.entity.HiringBoardEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface HiringRepository extends JpaRepository<HiringBoardEntity, Long> {

    // JPQL을 사용하여 HiringBoard를 조회할 때 StoreInfo를 즉시 함께(EAGERLY) 가져옵니다.
    @Query("SELECT DISTINCT h, d.dtlCdNm FROM HiringBoardEntity h " +
            "JOIN FETCH h.storeInfo " +
            "JOIN CmnCdDtlEntity d ON h.hiringSts = d.dtlCd AND d.mstrCd = 'HIRING_STS' " +
            "WHERE h.hiringNo = :id")
    List<Object[]> findByIdWithStoreInfo(@Param("id") Long id);

    @Query("SELECT h FROM HiringBoardEntity h " +
            "JOIN FETCH h.storeInfo " + // 기존 상점 정보
//            "LEFT JOIN FETCH h.applyList al " + // 지원 정보 (지원자가 없을 수도 있으니 LEFT JOIN 추천)
//            "LEFT JOIN FETCH al.userInfo " + // 지원한 유저 정보
            "WHERE h.hiringNo = :hiringNo")
    Optional<HiringBoardEntity> findDetailWithApplyAndUser(@Param("hiringNo") Long hiringNo);

//    @Modifying
//    @Query(value = "UPDATE hiring_board SET " +
//                    "HIRING_STS = '02', " +
//                    "updt_date = NOW(), " +
//                    "updt_id = :userId " +
//                    "WHERE HIRING_NO = :hiringNo",
//            nativeQuery = true) // 이 옵션이 핵심입니다!
//    int updateStatusHiringBoard(@Param("userId") String userId, @Param("hiringNo") Long hiringNo);
//
//    @Modifying
//    @Query(value = "UPDATE apply_info " +
//            "SET apply_suc_yn = 'Y', " +
//            "    updt_date = NOW(), " +
//            "    updt_id = :userId, " +
//            "    apply_sts = :applySts "+
//            "WHERE apply_no in (:applyNos) " +
//            "  AND hiring_no = :hiringNo", nativeQuery = true)
//    int updateStatusApplyInfo(@Param("userId") String userId, @Param("applyNos") List<Long> applyNos, @Param("hiringNo") Long hiringNo, @Param("applySts") String applySts);

    Page<HiringBoardEntity> findAllByOrderByRgstDateDesc(Pageable pageable);

    @Query(value="SELECT h, d.dtlCdNm \n" +
               "FROM HiringBoardEntity h " +
               "JOIN FETCH h.storeInfo " +
               "JOIN CmnCdDtlEntity d ON h.hiringSts = d.dtlCd AND d.mstrCd = 'HIRING_STS'" +
               "ORDER BY h.rgstDate DESC",
            countQuery = "SELECT count(DISTINCT h) "  +
                           "FROM HiringBoardEntity h " +
                           "JOIN CmnCdDtlEntity d ON h.hiringSts = d.dtlCd " +
                          "AND d.mstrCd = 'HIRING_STS'")
    Page<Object[]> findAllWithCodeName(Pageable pageable);
}
