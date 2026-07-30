package restapi.prac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.entity.ApplyEntity;
import restapi.prac.model.entity.NotificationEntity;
import restapi.prac.service.ApplyService;
import restapi.prac.service.NotificationService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/noti")
@Slf4j
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getNotifications")
    public ResponseEntity<List<NotificationEntity>> getMyNotifications(@RequestParam String userId){
        List<NotificationEntity> notifications = notificationService.getMyNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // 알림 단 건 클릭 시
    @PostMapping("/markAsRead")
    public ResponseEntity<String> markAsRead(@RequestParam Long notiNo){
        notificationService.markAsRead(notiNo);
        return ResponseEntity.ok("success");
    }

    // 전체 알림 클릭 시
    @PostMapping("/markAllAsRead")
//    @GetMapping("/markAllAsRead")
    public ResponseEntity<String> markAllAsread(@RequestParam String userId){
        System.out.println("GET 요청 컨트롤러 진입 성공! userId: " + userId);
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok("success");
    }

}
