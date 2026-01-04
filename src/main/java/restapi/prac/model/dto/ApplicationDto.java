package restapi.prac.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplicationDto {

    private Long storeId;     // 매장 ID
    private String storeNm;   // 매장명
    private String status;    // 상태 코드 ('01'~'09')
    private String applyDate; // 지원일

}
