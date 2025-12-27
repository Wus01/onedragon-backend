package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restapi.prac.model.UserInfo;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
    UserInfo findByUserId(String userId);

    Optional<UserInfo> findByUserEmail(String userEmail);

    Optional<UserInfo> findByUserIdAndUserEmail(String userId, String userEmail);
}
