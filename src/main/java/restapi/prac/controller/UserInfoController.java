package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.model.UserInfo;
import restapi.prac.service.UserInfoService;

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
}
