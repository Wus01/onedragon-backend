package restapi.prac.service;

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

    //상세조회
    public Optional<HiringBoard> getPost(Long id){

        return hiringRepository.findById(id);
    }
}
