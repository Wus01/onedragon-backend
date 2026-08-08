package restapi.prac.controller;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.common.ApiResponse;
import restapi.prac.model.dto.ApplicationDto;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.response.MyPageResponse;
import restapi.prac.service.JwtService;
import restapi.prac.service.MypageService;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final MypageService mypageService;
    private final JwtService jwtService;

    /**
     * 마이페이지 조회
     * - 회원 정보
     * - 내가 지원한 공고 리스트
     */
    @GetMapping
    public ResponseEntity<ApiResponse<MyPageResponse>> getMyPage(
//            @PathVariable String userId
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtService.getUserIdFromToken(token);

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

    /**
     * 마이페이지 조회
     * - 내가 지원한 공고 리스트
     */
    @GetMapping("/myApplyList")
    public ResponseEntity<?> selectMyApplyList(
            @RequestHeader("Authorization") String authHeader) {

//        String userId = applicationDto.getUserId();
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtService.getUserIdFromToken(token);

        List<ApplyDTO> applyList = mypageService.selectMyApplyList(userId);
        return ResponseEntity.ok(applyList);
    }

    /**
     * 마이페이지 조회
     * - 내가 올린 공고 리스트
     */
    @GetMapping("/myHiringList")
    public ResponseEntity<?> selectMyHiringList(
            @RequestHeader("Authorization") String authHeader) {
//        String userId = hiringBoardDTO.getUserId();
        String token = authHeader.replace("Bearer ", "");

        String userId = jwtService.getUserIdFromToken(token);
        List<HiringBoardDTO> applyList = mypageService.selectMyHiringList(userId);
        return ResponseEntity.ok(applyList);
    }
}
