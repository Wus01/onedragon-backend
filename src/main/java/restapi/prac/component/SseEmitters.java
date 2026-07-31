package restapi.prac.component;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitters {
    // 유저 ID를 키로, 연결된 파이프(SseEmitter)를 값으로 저장합니다.
    // 스레드 안전성을 위해 ConcurrentHashMap을 사용합니다.
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter add(String userId, SseEmitter emitter) {
        this.emitters.put(userId, emitter);

        // 연결이 끊기거나 타임아웃이 발생하면 맵에서 삭제합니다.
        emitter.onCompletion(() -> this.emitters.remove(userId));
        emitter.onTimeout(() -> emitter.complete());

        return emitter;
    }

    public SseEmitter get(String userId) {
        return this.emitters.get(userId);
    }

    public void send(String userId, Object data) {
        // 1. 맵에서 해당 유저의 SseEmitter를 꺼냅니다.
        SseEmitter emitter = emitters.get(userId);

        // 2. 유저가 현재 접속 중이라서 emitter가 존재한다면
        if (emitter != null) {
            try {
                // 3. 메시지를 전송합니다. (이름은 "notification" 등으로 자유롭게 설정)
                emitter.send(SseEmitter.event()
                        .name("notification") // 프론트엔드의 addEventListener("notification")과 매칭됨
                        .data(data));
            } catch (IOException e) {
                // 4. 전송 중 에러가 발생하면(클라이언트 브라우저가 닫혔거나 네트워크 끊김) 맵에서 삭제
                System.out.println("🔥 SSE 연결 끊김 (알림 전송 실패) - userId: " + userId);
                emitters.remove(userId);
            }
        }
    }
    public void remove(String userId) {
        this.emitters.remove(userId);
    }
}
