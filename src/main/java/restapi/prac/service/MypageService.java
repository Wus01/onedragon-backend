package restapi.prac.service;

import org.springframework.stereotype.Service;
import restapi.prac.model.dto.ApplicationDto;
import restapi.prac.model.response.MyPageResponse;
import restapi.prac.repository.MypageRepository;
import restapi.prac.repository.mapper.MyPageMapper;

import java.util.List;
import java.util.Optional;

@Service
public class MypageService {

    private final MypageRepository mypageRepository; // JPA
    private final MyPageMapper myPageMapper;         // MyBatis

    public MypageService(MypageRepository mypageRepository,
                         MyPageMapper myPageMapper) {
        this.mypageRepository = mypageRepository;
        this.myPageMapper = myPageMapper;
    }

    /**
     * 마이페이지 조회
     *  - 회원 정보
     *  - 내가 지원한 공고 리스트
     */
    public Optional<MyPageResponse> findByUserId(String userId) {

        return mypageRepository.findById(userId)
                .map(user -> {

                    List<ApplicationDto> applications =
                            myPageMapper.selectApplicationsByUserId(userId);

                    return new MyPageResponse(user, applications);
                });
    }
}
