package restapi.prac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import restapi.prac.model.ApplyInfo;
import restapi.prac.model.Hiring;
import restapi.prac.service.HiringService;

import java.util.Optional;

/*
 * RESTful API 설계 원칙에 따라, 기존 리소스를 생성(Create)할 때는 POST, 조회(Read)할 때는 GET, 수정(Update)할 때는 PUT 또는 PATCH, 삭제(Delete)할 때는 DELETE 메서드를 사용하는 것이 일반적입니다.
 * */
@RestController
//@RequestMapping("/api/hiring")
public class HiringController {
    @Autowired
    private HiringService hiringService;

    //상세보기
    @GetMapping("/hiring/{id}")
    public ResponseEntity<Hiring> getPost(@PathVariable Long id){
        Optional<Hiring> hiringOpt = hiringService.getPost(id);
        return hiringOpt.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }
}
