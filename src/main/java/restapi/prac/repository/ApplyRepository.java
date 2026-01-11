package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restapi.prac.model.ApplyInfo;
import restapi.prac.model.HiringBoard;
import restapi.prac.service.ApplyService;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplyRepository extends JpaRepository<ApplyInfo, Long> {

    /**
     * 특정 공고 번호(hiringNo)에 해당하는 모든 지원 정보(ApplyInfo) 목록을 조회합니다.
     * * @param hiringNo 조회할 공고의 ID
     * @return 해당 공고에 지원한 모든 지원자 목록
     */

    @Query("SELECT DISTINCT b FROM ApplyInfo b " +
            "JOIN FETCH b.userInfo c " +
            "LEFT JOIN FETCH c.crrHstrList d " +  // UserInfo에 있는 경력 리스트 (필드명 확인 필요)
            "LEFT JOIN FETCH d.storeInfo e " +     // 경력 엔티티에 있는 매장 정보
            "WHERE b.hiringBoard.hiringNo = :hiringNo")
    List<ApplyInfo> findByHiringNoWithUserInfo(@Param("hiringNo") Long hiringNo);

}
