package restapi.prac.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import restapi.prac.model.dto.ApplicationDto;
import restapi.prac.model.dto.response.ApplyDTO;
import restapi.prac.model.dto.response.HiringBoardDTO;

import java.util.List;

@Mapper
public interface MyPageMapper {

    /**
     * 마이페이지 - 내가 지원한 공고 조회 (26.07.14. 경력조회인 거 같음)
     */
    List<ApplicationDto> selectApplicationsByUserId(String userId);

    /**
     * 마이페이지 - 내가 지원한 공고 조회
     */
    List<ApplyDTO> selectMyApplyList(String userId);

    /**
     * 마이페이지 - 내 공고 목록 조회
     */
    List<HiringBoardDTO> selectMyHiringList(String userId);
}
