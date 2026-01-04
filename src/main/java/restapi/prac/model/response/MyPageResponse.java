package restapi.prac.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import restapi.prac.model.UserInfo;
import restapi.prac.model.dto.ApplicationDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class MyPageResponse {

    private UserInfo userInfo;                 // 회원 정보
    private List<ApplicationDto> applications; // 지원 공고 리스트

}
