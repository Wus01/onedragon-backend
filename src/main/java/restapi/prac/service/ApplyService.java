package restapi.prac.service;

import org.springframework.stereotype.Service;
import restapi.prac.model.ApplyInfo;
import restapi.prac.model.HiringBoard;
import restapi.prac.repository.ApplyRepository;
import restapi.prac.repository.HiringRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ApplyService {

    private final ApplyRepository applyRepository;

    public ApplyService(ApplyRepository applyRepository){
        this.applyRepository = applyRepository;
    }

    //상세조회
    public Optional<ApplyInfo> getPost(Long id){

        return applyRepository.findById(id);
    }

    //공고별 지원자리스트 조회
    public List<ApplyInfo> getApplicantsByHiringId(Long id){

        return applyRepository.findByHiringNo(id);
    }
}
