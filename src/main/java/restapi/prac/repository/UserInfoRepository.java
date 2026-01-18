package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.UserInfoEntity;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, String> {
    UserInfoEntity findByUserId(String userId);

    Optional<UserInfoEntity> findByUserEmail(String userEmail);

    Optional<UserInfoEntity> findByUserIdAndUserEmail(String userId, String userEmail);
}
