package restapi.prac.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.common.ApiResponse;
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



    @PutMapping("/{userId}/{storeId}")
    public ResponseEntity<ApiResponse<CrrHstrResponseDto>> updateCrrHstr(
            @PathVariable String userId,
            @PathVariable Integer storeId,
            @RequestBody CrrHstrRequestDto dto) {

        // 서비스 호출하여 수정된 결과 DTO를 받음
        CrrHstrResponseDto updatedData = crrHstrService.updateCrrHstr(userId, storeId, dto);
        return ResponseEntity.ok(ApiResponse.ok(updatedData));
    }

    // DELETE
    @DeleteMapping("/{userId}/{storeId}")
    public ResponseEntity<ApiResponse<CrrHstrResponseDto>> deleteCrrHstr(
            @PathVariable String userId,
            @PathVariable Integer storeId,
            @RequestBody CrrHstrRequestDto dto) { // DTO를 통해 로컬스토리지의 userId 수신

        CrrHstrResponseDto deletedInfo = crrHstrService.deleteCrrHstr(userId, storeId, dto);

        return ResponseEntity.ok(ApiResponse.ok(deletedInfo));
    }

    // INSERT
    @PostMapping
    @Operation(summary = "재직이력 입력", description = "userId와 storeId로 회원의 재직 이력을 입력합니다.")
    public ResponseEntity<CrrHstrResponseDto> createCrrHstr(@RequestBody CrrHstrRequestDto dto) {
        // 1. 서비스 단에서 DTO를 받아 로직 처리 (rgstId, updtId 수동 매핑 포함)
        CrrHstrResponseDto createdPost = crrHstrService.createCrrHstr(dto);
        return ResponseEntity.ok(createdPost);
    }
}

