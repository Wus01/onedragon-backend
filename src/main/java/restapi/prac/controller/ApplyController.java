package restapi.prac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.model.dto.response.HiringBoardDTO;
import restapi.prac.model.entity.ApplyEntity;
import restapi.prac.model.entity.HiringBoardEntity;
import restapi.prac.service.ApplyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/apply")
@Slf4j
public class ApplyController {
    @Autowired
    private ApplyService applyService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplyEntity> getPost(@PathVariable Long id){
        // 이 로그가 서버 콘솔에 찍히지 않았다면, Spring Data REST에 의해 요청이 가로채인 것입니다.
        // 해당 쿼리는 Service/Repository가 아닌 자동 생성된 엔드포인트에 의해 호출되었을 가능성이 높습니다.
        log.debug("DEBUG: getPost 메서드 호출 시도. ID: " + id);

        Optional<ApplyEntity> applyOpt = applyService.getPost(id);

        // 이 로그가 찍힌다면 Service는 정상 호출된 것입니다.
        log.info("DEBUG: Service 호출 완료.");
        if (applyOpt.isPresent()) {
            log.debug("DEBUG: 데이터 발견, 200 OK 반환 예정");
            return ResponseEntity.ok(applyOpt.get());
        } else {
            log.error("DEBUG: 데이터 없음, 404 Not Found 반환 예정");
            return ResponseEntity.notFound().build();
        }

    }

//    @GetMapping("/list/{id}")
//    public ResponseEntity<List<ApplyInfo>> getApplicantsByHiringId(@PathVariable Long id){
//        // 1. Service에서 리스트를 가져오는 새로운 메서드를 호출해야 합니다.
//        //    (이전에 논의했던 applyService.getApplicantsByHiringId(hiringId) 사용)
//        List<ApplyInfo> applierList = applyService.getApplicantsByHiringId(id);
//
//        System.out.println("DEBUG: 지원자 목록 조회 완료. 개수: " + applierList.size());
//
//        if (applierList.isEmpty()) {
//            // 데이터가 없을 경우: 204 No Content 또는 200 OK와 빈 리스트 반환 (프론트엔드 처리 용이)
//            return ResponseEntity.ok(applierList); // 200 OK와 빈 리스트 [] 반환
//        }
//
//        // 2. 리스트가 존재할 경우: 200 OK와 함께 목록을 반환
//        return ResponseEntity.ok(applierList);
//
//    }

    @GetMapping("/list/{hiringNo}")
    public ResponseEntity<List<ApplyEntity>> getApplyListByHiringNo(@PathVariable Long hiringNo) {
        List<ApplyEntity> applierList = applyService.getApplyListByHiringNo(hiringNo);

        // 리스트가 비어있어도 ok(applierList)를 하면 프론트에 []가 가니까 괜찮습니다.
        // 로그는 찍어두면 디버깅할 때 아주 편해요!
        log.debug("DEBUG: 공고번호 " + hiringNo + "의 지원자 수: " + applierList.size());

        return ResponseEntity.ok(applierList);
    }

    // 지원하기
    @PostMapping("/insertApply")
    public ResponseEntity<?> insertApplyInfo(@RequestBody ApplyDTO applyDto){
        try {
            ApplyEntity insertApplyInfo = applyService.insertApplyInfo(applyDto);
            return ResponseEntity.ok("지원성공");
        }catch (Exception e){
            return ResponseEntity.status(500).body("지원 처리 중 오류 발생: " + e.getMessage());
        }

    }

    /**
     * 확정처리
     */
    @PostMapping("/confirm")
    public ResponseEntity<?> confirmApply(@RequestBody HiringBoardDTO hiringBoardDTO) {
        try {
            // 서비스 단의 업데이트 로직 실행
            applyService.confirmApply(hiringBoardDTO);

            return ResponseEntity.ok("성공적으로 확정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("확정 처리 중 오류 발생: " + e.getMessage());
        }
    }
}
