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

}
