package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.CrrHstrId;
import restapi.prac.model.CrrHstrVo;

@Repository
public interface CrrHstrRepository extends JpaRepository<CrrHstrVo, CrrHstrId> {



}
