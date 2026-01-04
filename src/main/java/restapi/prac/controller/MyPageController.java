package restapi.prac.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.common.ApiResponse;
import restapi.prac.model.response.MyPageResponse;
import restapi.prac.service.MypageService;

@RestController
@RequestMapping("/api/mypage")
public class MyPageController {

    private final MypageService mypageService;

    public MyPageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    /**
     * 마이페이지 조회
     * - 회원 정보
     * - 내가 지원한 공고 리스트
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<MyPageResponse>> getMyPage(
            @PathVariable String userId) {

        return mypageService.findByUserId(userId)
                .map(result ->
                        ResponseEntity.ok(
                                ApiResponse.ok(result)
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
