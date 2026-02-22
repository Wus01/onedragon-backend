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

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Map<String, Object>>> searchStores(
            @RequestParam(required = false) String type,    // nm 또는 addr
            @RequestParam(required = false) String keyword, // 검색어
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 서비스 호출 시 type과 keyword를 함께 전달
        Page<StoreEntity> storePage = storeService.searchStore(type, keyword, page, size);

        Map<String, Object> result = new HashMap<>();
        result.put("items", storePage.getContent());
        result.put("totalCount", storePage.getTotalElements());
        result.put("totalPages", storePage.getTotalPages());

        return ResponseEntity.ok(ApiResponse.ok(result));
    }


}
