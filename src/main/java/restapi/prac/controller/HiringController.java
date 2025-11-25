package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.prac.model.HiringBoard;
import restapi.prac.service.HiringService;

import java.util.Optional;

/*
 * RESTful API 설계 원칙에 따라, 기존 리소스를 생성(Create)할 때는 POST, 조회(Read)할 때는 GET, 수정(Update)할 때는 PUT 또는 PATCH, 삭제(Delete)할 때는 DELETE 메서드를 사용하는 것이 일반적입니다.
 * */
@RestController
@RequestMapping("api/hiring")
public class HiringController {
    @Autowired
    private HiringService hiringService;

    //상세보기
//    @GetMapping("/hiring/{id}")
//    @GetMapping("/{id}")
//    public ResponseEntity<Hiring> getPost(@PathVariable Long id){
//        Optional<Hiring> hiringOpt = hiringService.getPost(id);
//        return hiringOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
//    }

    // 상세보기를 위한 최종 코드
    @GetMapping("/{id}")
    public ResponseEntity<HiringBoard> getPost(@PathVariable Long id){
        // 이 로그가 서버 콘솔에 찍히지 않았다면, Spring Data REST에 의해 요청이 가로채인 것입니다.
        // 해당 쿼리는 Service/Repository가 아닌 자동 생성된 엔드포인트에 의해 호출되었을 가능성이 높습니다.
        System.out.println("DEBUG: getPost 메서드 호출 시도. ID: " + id);

        Optional<HiringBoard> hiringOpt = hiringService.getPost(id);

        // 이 로그가 찍힌다면 Service는 정상 호출된 것입니다.
        System.out.println("DEBUG: Service 호출 완료.");
        if (hiringOpt.isPresent()) {
            System.out.println("DEBUG: 데이터 발견, 200 OK 반환 예정");
            return ResponseEntity.ok(hiringOpt.get());
        } else {
            System.out.println("DEBUG: 데이터 없음, 404 Not Found 반환 예정");
            return ResponseEntity.notFound().build();
        }

//        return hiringOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
