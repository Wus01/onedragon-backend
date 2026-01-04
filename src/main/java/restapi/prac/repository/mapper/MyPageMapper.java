package restapi.prac.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import restapi.prac.model.dto.ApplicationDto;

import java.util.List;

@Mapper
public interface MyPageMapper {

    /**
     * 마이페이지 - 내가 지원한 공고 조회
     */
    List<ApplicationDto> selectApplicationsByUserId(String userId);
}
