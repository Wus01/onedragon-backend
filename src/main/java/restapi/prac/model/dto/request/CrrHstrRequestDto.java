package restapi.prac.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CrrHstrRequestDto {

    private String userId;
    private Integer storeId;
    private String crrStrtDate;
    private String crrEndDate;
    private Boolean authYn;  // 기존의 Y/N 체크용
    private String status;
    private boolean delYn;
}
