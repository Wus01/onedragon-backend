package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.HiringBoard;

@Repository
public interface HiringRepository extends JpaRepository<HiringBoard, Long> {



}
