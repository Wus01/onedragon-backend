package restapi.prac.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import restapi.prac.component.SseEmitters;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.entity.ApplyEntity;
import restapi.prac.model.entity.NotificationEntity;
import restapi.prac.service.ApplyService;
import restapi.prac.service.NotificationService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/noti")
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    private final SseEmitters sseEmitters;

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

    // 1️⃣ 프론트엔드에서 파이프를 연결하는 API
    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> subscribe(@PathVariable String userId) {

        // 1. SseEmitter 생성 (타임아웃 설정: 예시로 1시간)
        Long timeout = 60L * 1000 * 60;
        SseEmitter emitter = new SseEmitter(timeout);

        // 2. SseEmitters 맵에 해당 유저의 emitter 저장 (추가)
        sseEmitters.add(userId, emitter);

        // 3. 시간이 만료되거나, 클라이언트 창이 닫히면 맵에서 삭제되도록 콜백 설정
        emitter.onTimeout(() -> sseEmitters.remove(userId));
        emitter.onCompletion(() -> sseEmitters.remove(userId));

        // 4. [중요] 연결 직후 503 에러 방지를 위해 더미(Dummy) 데이터 하나 보내주기
        try {
            emitter.send(SseEmitter.event()
                    .name("connect") // 연결 확인용 이름
                    .data("SSE Connected!"));
        } catch (Exception e) {
            sseEmitters.remove(userId);
        }

        return ResponseEntity.ok(emitter);
    }

}
