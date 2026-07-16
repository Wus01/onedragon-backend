package restapi.prac.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.common.ApiResponse;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.entity.CrrHstrEntity;
import restapi.prac.model.dto.request.CrrHstrRequestDto;
import restapi.prac.model.dto.response.CrrHstrResponseDto;
import restapi.prac.service.CrrHstrService;

import java.util.Optional;

@RestController
@RequestMapping("/api/crrHstr")
public class CrrHstrController {
/*
* RESTful API 설계 원칙에 따라, .
* 기존 리소스를 생성(Create)할 때는 POST,
* 조회(Read)할 때는 GET, 수정(Update)할 때는 PUT 또는 PATCH,
* 삭제(Delete)할 때는 DELETE 메서드를 사용하는 것이 일반적입니다.
* */
    @Autowired
    private CrrHstrService crrHstrService;

    @GetMapping("/{userId}/{storeId}")
    @Operation(summary = "재직이력 조회", description = "userId와 storeId로 회원의 재직 이력을 조회합니다.")
    public ResponseEntity<ApiResponse<CrrHstrResponseDto>> getCrrHstr(@PathVariable String userId,
                                                                      @PathVariable Integer storeId) {
        Optional<CrrHstrEntity> crrHstrOpt = crrHstrService.getCrrHstr(userId, storeId);

        // 2. DTO 변환 및 응답
        return crrHstrOpt
                .map(entity -> {
                    CrrHstrResponseDto responseDto = CrrHstrResponseDto.from(entity);
                    return ResponseEntity.ok(ApiResponse.ok(responseDto));
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("재직 이력이 없습니다.")));
    }



//    @PutMapping("/{userId}/{storeId}")
//    public ResponseEntity<ApiResponse<CrrHstrResponseDto>> updateCrrHstr(
//            @PathVariable String userId,
//            @PathVariable Integer storeId,
//            @RequestBody CrrHstrRequestDto dto) {
//
//        // 서비스 호출하여 수정된 결과 DTO를 받음
//        CrrHstrResponseDto updatedData = crrHstrService.updateCrrHstr(userId, storeId, dto);
//        return ResponseEntity.ok(ApiResponse.ok(updatedData));
//    }

    @PutMapping("/update/{crrHstrNo}")
    public ResponseEntity<CrrHstrEntity> updateCrrHstr(
            @PathVariable Long crrHstrNo,
            @RequestBody CrrHstrRequestDto dto) {
//        Long crrHstrNo = pathCrrHstrNo;
        // 서비스 호출하여 수정된 결과 DTO를 받음
        CrrHstrEntity updatedData = crrHstrService.updateCrrHstr(crrHstrNo, dto);
        return ResponseEntity.ok(updatedData);
    }

    // DELETE
    @DeleteMapping("/delete/{crrHstrNo}")
    public ResponseEntity<String> deleteCrrHstr(
            @PathVariable Long crrHstrNo,
            @RequestBody CrrHstrRequestDto dto) { // DTO를 통해 로컬스토리지의 userId 수신

        crrHstrService.deleteCrrHstr(crrHstrNo, dto);

        return ResponseEntity.ok("삭제 완료");
    }

    // INSERT
    @PostMapping
    @Operation(summary = "재직이력 입력", description = "userId와 storeId로 회원의 재직 이력을 입력합니다.")
    public ResponseEntity<CrrHstrResponseDto> createCrrHstr(@RequestBody CrrHstrRequestDto dto) {
        // 1. 서비스 단에서 DTO를 받아 로직 처리 (rgstId, updtId 수동 매핑 포함)
        CrrHstrResponseDto createdPost = crrHstrService.createCrrHstr(dto);
        return ResponseEntity.ok(createdPost);
    }

    // 마이페이지 내 경력목록 조회(다건)
    @GetMapping("/{userId}")
    @Operation(summary = "재직이력 조회", description = "userId로 회원의 재직 이력을 조회합니다.")
    public ResponseEntity<ApiResponse<CrrHstrResponseDto>> selectMyCrrHstrList(@PathVariable String userId) {
        Optional<CrrHstrEntity> crrHstrOpt = crrHstrService.selectMyCrrHstrList(userId);

        // 2. DTO 변환 및 응답
        return crrHstrOpt
                .map(entity -> {
                    CrrHstrResponseDto responseDto = CrrHstrResponseDto.from(entity);
                    return ResponseEntity.ok(ApiResponse.ok(responseDto));
                })
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("재직 이력이 없습니다.")));
    }

    // 경력 페이지 단건 조회
    @GetMapping("/select/{crrHstrNo}")
    @Operation(summary = "재직이력 상세 조회", description = "crrHstrNo로 회원의 재직 이력 상세를 조회합니다.")
    public ResponseEntity<CrrHstrEntity> selectOneCrrHstr(@PathVariable Long crrHstrNo) {

        CrrHstrEntity crrHstrEntity = crrHstrService.selectOneCrrHstr(crrHstrNo);

        return ResponseEntity.ok(crrHstrEntity);

    }

}

