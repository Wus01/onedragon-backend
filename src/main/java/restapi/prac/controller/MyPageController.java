package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.common.ApiResponse;
import restapi.prac.model.UserInfo;
import restapi.prac.service.MypageService;


@RestController
@RequestMapping("/api/mypage")
public class MyPageController {
/*
* RESTful API 설계 원칙에 따라, .
* 기존 리소스를 생성(Create)할 때는 POST,
* 조회(Read)할 때는 GET, 수정(Update)할 때는 PUT 또는 PATCH,
* 삭제(Delete)할 때는 DELETE 메서드를 사용하는 것이 일반적입니다.
* */
    @Autowired
    private MypageService mypageService;

    // 마이페이지 회원조회
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfo>> getMyPage(@PathVariable String userId) {
        return mypageService.findByUserId(userId)
                .map(userInfo ->
                        ResponseEntity.ok(
                                ApiResponse.ok(userInfo)
                        )
                )
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(
                                        ApiResponse.error("회원 정보가 없습니다.")
                                )
                );
    }
}