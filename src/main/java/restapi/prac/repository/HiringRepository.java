package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.Hiring;

@Repository
public interface HiringRepository extends JpaRepository<Hiring, Long> {



}
