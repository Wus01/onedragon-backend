package restapi.prac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restapi.prac.model.entity.NotificationEntity;

import java.util.List;


@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByRcvrIdOrderByNotiNoDesc(String rcvrId);

    // 알림 읽음처리
    @Modifying(clearAutomatically = true)
    @Query("UPDATE NotificationEntity n SET n.readYn = 'Y' WHERE n.rcvrId = :rcvrId AND n.readYn = 'N'")
    int markAllAsRead(@Param("rcvrId") String rcvrId);
}
