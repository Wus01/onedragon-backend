package restapi.prac.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restapi.prac.model.UserInfo;
import restapi.prac.repository.UserInfoRepository;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    // 저장
    public UserInfo createUserInfo(UserInfo userInfo){
        // 등록일시, 등록아이디 세팅
        userInfo.setRgstDate(new Date());
        userInfo.setRgstId(userInfo.getUserId());

        // 점주인증구분, 회원탈퇴여부, 점주알바생구분 디폴트값 세팅
        userInfo.setEmployerAuthYn(Optional.ofNullable(userInfo.getEmployerAuthYn()).orElse("N"));
        userInfo.setUserDelYn(Optional.ofNullable(userInfo.getUserDelYn()).orElse("N"));
        userInfo.setUserType(Optional.ofNullable(userInfo.getUserType()).orElse("E"));

        // 약관동의 여부
        if (userInfo.getAgreementTerms() == null) userInfo.setAgreementTerms(false);

        // 비밀번호 해시
        String encoded = passwordEncoder.encode(userInfo.getUserPwd());
        userInfo.setUserPwd(encoded);

        return userInfoRepository.save(userInfo);
    }
}
