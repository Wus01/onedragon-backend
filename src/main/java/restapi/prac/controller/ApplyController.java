package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restapi.prac.model.ApplyInfo;
import restapi.prac.service.ApplyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/apply")
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplyInfo> getPost(@PathVariable Long id){
        // 이 로그가 서버 콘솔에 찍히지 않았다면, Spring Data REST에 의해 요청이 가로채인 것입니다.
        // 해당 쿼리는 Service/Repository가 아닌 자동 생성된 엔드포인트에 의해 호출되었을 가능성이 높습니다.
        System.out.println("DEBUG: getPost 메서드 호출 시도. ID: " + id);

        Optional<ApplyInfo> applyOpt = applyService.getPost(id);

        // 이 로그가 찍힌다면 Service는 정상 호출된 것입니다.
        System.out.println("DEBUG: Service 호출 완료.");
        if (applyOpt.isPresent()) {
            System.out.println("DEBUG: 데이터 발견, 200 OK 반환 예정");
            return ResponseEntity.ok(applyOpt.get());
        } else {
            System.out.println("DEBUG: 데이터 없음, 404 Not Found 반환 예정");
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/list/{id}")
    public ResponseEntity<List<ApplyInfo>> getApplicantsByHiringId(@PathVariable Long id){
        // 1. Service에서 리스트를 가져오는 새로운 메서드를 호출해야 합니다.
        //    (이전에 논의했던 applyService.getApplicantsByHiringId(hiringId) 사용)
        List<ApplyInfo> applierList = applyService.getApplicantsByHiringId(id);

        System.out.println("DEBUG: 지원자 목록 조회 완료. 개수: " + applierList.size());

        if (applierList.isEmpty()) {
            // 데이터가 없을 경우: 204 No Content 또는 200 OK와 빈 리스트 반환 (프론트엔드 처리 용이)
            return ResponseEntity.ok(applierList); // 200 OK와 빈 리스트 [] 반환
        }

        // 2. 리스트가 존재할 경우: 200 OK와 함께 목록을 반환
        return ResponseEntity.ok(applierList);

    }
}
