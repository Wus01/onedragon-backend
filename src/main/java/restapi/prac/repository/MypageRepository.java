package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.entity.UserInfoEntity;

@Repository
public interface MypageRepository extends JpaRepository<UserInfoEntity, String> {


}
