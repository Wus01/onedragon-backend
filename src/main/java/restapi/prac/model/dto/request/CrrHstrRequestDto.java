package restapi.prac.model.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import restapi.prac.model.entity.CrrHstrEntity;

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

    public CrrHstrEntity toEntity() {
        return CrrHstrEntity.builder()
                .userId(this.userId)
                .storeId(this.storeId)
                .crrStrtDate(this.crrStrtDate)
                .crrEndDate(this.crrEndDate)
                .status(this.status != null ? this.status : "01")
                .authYn(this.authYn != null ? this.authYn : false)
                .delYn(this.delYn)
                .build();
    }

}
