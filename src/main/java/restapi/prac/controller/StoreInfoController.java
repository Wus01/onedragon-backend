package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.common.ApiResponse;
import restapi.prac.model.entity.StoreEntity;
import restapi.prac.service.StoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
public class StoreInfoController {
    @Autowired
    private StoreService storeService;

    //점포 리스트 조회
    @GetMapping("/getStores")
    public ResponseEntity<Page<StoreEntity>> listStoreInfo(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<StoreEntity> stores = storeService.getStores(pageable);
        return ResponseEntity.ok().body(stores);
    }

    // 지점 데이터 리스트 저장
    @PostMapping("/batch")
    public ResponseEntity<List<StoreEntity>> createStore(@RequestBody List<StoreEntity> storeEntity){
        List<StoreEntity> createStores = storeService.createStoreList(storeEntity);
        return ResponseEntity.ok(createStores);
    }

    // 지점 검색 list 가져오기
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Map<String, Object>>> searchStores(
            @RequestParam(required = false) String nm,
            @RequestParam(defaultValue = "1") int page,  // 프론트에서 1번부터 보냄
            @RequestParam(defaultValue = "10") int size) {

        // 1. 서비스 호출 (Page 객체로 받는 것이 확장성에 좋음)
        Page<StoreEntity> storePage = storeService.searchStoreByName(nm, page, size);

        // 2. 프론트엔드 Pagination 요구사항에 맞게 Map 구성
        Map<String, Object> result = new HashMap<>();
        result.put("items", storePage.getContent());           // 현재 페이지 데이터 리스트
        result.put("totalCount", storePage.getTotalElements()); // 전체 검색 결과 수
        result.put("totalPages", storePage.getTotalPages());    // 전체 페이지 수

        return ResponseEntity.ok(ApiResponse.ok(result));
    }

}
