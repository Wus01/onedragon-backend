package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.StoreDO;

@Repository
public interface StoreRepository  extends JpaRepository<StoreDO, Long> {

}
