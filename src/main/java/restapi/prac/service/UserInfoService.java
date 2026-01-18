package restapi.prac.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import restapi.prac.model.entity.UserInfoEntity;
import restapi.prac.repository.UserInfoRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // 저장
    public UserInfoEntity createUserInfo(UserInfoEntity userInfo){
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

    // 로그인
    public UserInfoEntity login(String userId, String rawPassword) {
        UserInfoEntity user = userInfoRepository.findByUserId(userId);

        if (user == null) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // 저장된 비밀번호는 bcrypt 해시 → 평문과 비교해야 함
        if (!passwordEncoder.matches(rawPassword, user.getUserPwd())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 잘못되었습니다.");
        }

        // JWT 토큰 생성
        String generatedToken = jwtService.generateToken(userId);
        user.setToken(generatedToken);

        return user;
    }

    // 아이디 찾기
    public String findId(String userEmail) {
        UserInfoEntity user = userInfoRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("해당 이메일로 등록된 아이디가 없습니다."));

        return user.getUserId();
    }

    // 비밀번호 찾기
    public String findPw(String userId, String userEmail) {
        UserInfoEntity user = userInfoRepository.findByUserIdAndUserEmail(userId, userEmail)
                .orElseThrow(() -> new RuntimeException("입력하신 정보와 일치하는 계정이 없습니다."));

        // 임시 비밀번호 생성
        String tempPassword = UUID.randomUUID().toString().substring(0, 8);

        // 비밀번호 암호화 후 DB 업데이트
        String encodedPassword = passwordEncoder.encode(tempPassword);
        user.setUserPwd(encodedPassword);
        userInfoRepository.save(user); // 새로운 암호로 덮어쓰기

        return tempPassword;
    }
}
