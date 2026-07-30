package restapi.prac.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.entity.ApplyEntity;
import restapi.prac.model.entity.HiringBoardEntity;
import restapi.prac.model.entity.NotificationEntity;
import restapi.prac.model.entity.UserInfoEntity;
import restapi.prac.repository.ApplyRepository;
import restapi.prac.repository.HiringRepository;
import restapi.prac.repository.NotificationRepository;
import restapi.prac.repository.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional(readOnly = true)
    public List<NotificationEntity> getMyNotifications(String userId){
        return notificationRepository.findByRcvrIdOrderByNotiNoDesc(userId);
    }

    // 알림 단 건 클릭 시
    @Transactional
    public void markAsRead(Long notiNo){
        NotificationEntity notification = notificationRepository.findById(notiNo)
                .orElseThrow(()-> new IllegalArgumentException("해당 알림이 존재하지 않습니다. 알림번호: " + notiNo));

        notification.setReadYn("Y");
        // 이후 알아서 readYn 업데이트 처리 함
    }

    // 전체 알림 클릭 시
    @Transactional
    public void markAllAsRead(String userId){
        notificationRepository.markAllAsRead(userId);
    }

}
