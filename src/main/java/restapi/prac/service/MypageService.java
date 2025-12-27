package restapi.prac.service;

import org.springframework.stereotype.Service;
import restapi.prac.model.UserInfo;
import restapi.prac.repository.MypageRepository;

import java.util.Optional;

@Service
public class MypageService {

    private final MypageRepository mypageRepository;

    public MypageService(MypageRepository mypageRepository){
        this.mypageRepository = mypageRepository;
    }


    /**
     * 마이페이지 회원조회
     */
    public Optional<UserInfo> findByUserId(String userId) {
        return mypageRepository.findById(userId);
    }

}
