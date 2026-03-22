package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.entity.HiringBoardEntity;

import java.util.Optional;

@Repository
public interface HiringRepository extends JpaRepository<HiringBoardEntity, Long> {

    // JPQL을 사용하여 HiringBoard를 조회할 때 StoreInfo를 즉시 함께(EAGERLY) 가져옵니다.
    @Query("SELECT DISTINCT h FROM HiringBoardEntity h JOIN FETCH h.storeInfo WHERE h.hiringNo = :id")
    Optional<HiringBoardEntity> findByIdWithStoreInfo(@Param("id") Long id);

    @Query("SELECT h FROM HiringBoardEntity h " +
            "JOIN FETCH h.storeInfo " + // 기존 상점 정보
//            "LEFT JOIN FETCH h.applyList al " + // 지원 정보 (지원자가 없을 수도 있으니 LEFT JOIN 추천)
//            "LEFT JOIN FETCH al.userInfo " + // 지원한 유저 정보
            "WHERE h.hiringNo = :hiringNo")
    Optional<HiringBoardEntity> findDetailWithApplyAndUser(@Param("hiringNo") Long hiringNo);

//    @Modifying
//    @Transactional // 보통 서비스단에서 걸지만, 개별 테스트를 위해 리포지토리에도 명시 가능
//    @Query("update hiring_board set HIRING_STS='02' where HIRING_NO= :id")
//    int updateStatusHiringBoard(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE hiring_board SET HIRING_STS = '02' WHERE HIRING_NO = :id",
            nativeQuery = true) // 이 옵션이 핵심입니다!
    int updateStatusHiringBoard(@Param("id") Long id);

    // 26.03.23 문법 오류나서 주석처리
//    @Modifying
//    @Transactional
//    @Query("update apply_info\n" +
//            "  set apply_suc_yn='Y'\n" +
//            "    , updt_date = now()\n" +
//            "    , updt_id = #{user_id} -- 세션값이어야함\n" +
//            "where user_id=#{user_id}\n" +
//            "  and hiring_no #{hiring_no}")
//    int updateStatusApplyInfo(@Param("id") Long id);

    // 26.03.23 위 코드를 수정한 내용임.
    // service에서 인자 1개 넘기는데 로직 상 userId랑 hiringId가 필요해보임.
    // 수정 plz.

//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE apply_info " +
//            "SET apply_suc_yn = 'Y', " +
//            "    updt_date = NOW(), " +
//            "    updt_id = :userId " +
//            "WHERE user_id = :userId " +
//            "  AND hiring_no = :hiringNo", nativeQuery = true)
//    int updateStatusApplyInfo(@Param("userId") String userId, @Param("hiringNo") Long hiringNo);
}
