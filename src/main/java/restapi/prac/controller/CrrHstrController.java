package restapi.prac.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.model.CrrHstrVo;
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

    // SELECT 1
    @GetMapping("/{userId}/{storeId}")
    @Operation(summary = "재직이력 조회", description = "userId와 storeId로 회원의 재직 이력을 조회합니다.")
    public ResponseEntity<CrrHstrVo> getCrrHstr(@PathVariable Integer userId
                                                ,@PathVariable Integer storeId){
        Optional<CrrHstrVo> crrHstrOpt = crrHstrService.getCrrHstr(userId,storeId);
        return crrHstrOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }


    // UPDATE
    @PutMapping("/{userId}/{storeId}")
    @Operation(summary = "재직이력 수정", description = "userId와 storeId로 회원의 재직 이력을 수정합니다.")
    public ResponseEntity<CrrHstrVo> updateCrrHstr(@PathVariable Integer userId
                                                    ,@PathVariable Integer storeId
                                                    ,@RequestBody CrrHstrVo updateCrrHstrVo){
        Optional<CrrHstrVo> updated = crrHstrService.updateCrrHstr(userId, storeId, updateCrrHstrVo);
        return updated.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{userId}/{storeId}")
    @Operation(summary = "재직이력 삭제", description = "userId와 storeId로 회원의 재직 이력을 삭제합니다.")
    public ResponseEntity<Void> deleteCrrHstr(@PathVariable Integer userId
                                                ,@PathVariable Integer storeId){
        boolean deleted = crrHstrService.deleteCrrHstr(userId, storeId);
        if(deleted){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    // INSERT
    @PostMapping
    @Operation(summary = "재직이력 입력", description = "userId와 storeId로 회원의 재직 이력을 입력합니다.")
    public ResponseEntity<CrrHstrVo> creatCrrHstr(@RequestBody CrrHstrVo crrHstrVo){
        CrrHstrVo createdPost = crrHstrService.createCrrHstr(crrHstrVo);
        return ResponseEntity.ok(createdPost);
    }

}

