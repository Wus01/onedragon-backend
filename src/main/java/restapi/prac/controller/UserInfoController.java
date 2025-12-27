package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.model.UserInfo;
import restapi.prac.service.UserInfoService;

import java.util.Map;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    // 회원가입 데이터 저장
    @PostMapping("/signup")
    public ResponseEntity<UserInfo> createUserInfo(@RequestBody UserInfo userInfo){
        UserInfo createUserInfo = userInfoService.createUserInfo(userInfo);
        return ResponseEntity.ok(createUserInfo);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> data) {
        String userId = data.get("userId");
        String userPwd = data.get("userPwd");

        try {
            UserInfo user = userInfoService.login(userId, userPwd);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }

    // 아이디 찾기
    @PostMapping("/findId")
    public ResponseEntity<?> findId(@RequestBody Map<String, String> data) {
        String userEmail = data.get("userEmail");

        try {
            String findId = userInfoService.findId(userEmail);
            return ResponseEntity.ok(Map.of("success", true, "userId", findId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    // 비밀번호 찾기
    @PostMapping("/findPw")
    public ResponseEntity<?> findPw(@RequestBody Map<String, String> data) {
        String userId = data.get("userId");
        String userEmail = data.get("userEmail");

        try {
            String findPw = userInfoService.findPw(userId, userEmail);
            return ResponseEntity.ok(Map.of("success", true, "userPwd", findPw));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}
