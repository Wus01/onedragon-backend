package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String userId = loginData.get("userId");
        String userPwd = loginData.get("userPwd");

        try {
            UserInfo user = userInfoService.login(userId, userPwd);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
        }
    }

}
