package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.model.entity.StoreEntity;
import restapi.prac.service.StoreService;

import java.util.List;

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
}
