package restapi.prac.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import restapi.prac.model.HiringBoard;
import restapi.prac.repository.HiringRepository;

import java.util.Optional;

@Service
public class HiringService {

    private final HiringRepository hiringRepository;
    public HiringService(HiringRepository hiringRepository){
        this.hiringRepository = hiringRepository;
    }

    // 전체조회
    public Page<HiringBoard> getHirings(Pageable pageable) {
        return hiringRepository.findAll(pageable);
    }
    //상세조회
    public Optional<HiringBoard> getPost(Long id){

        return hiringRepository.findByIdWithStoreInfo(id);
    }
}
