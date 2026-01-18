package restapi.prac.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import restapi.prac.model.entity.CrrHstrEntity;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrrHstrResponseDto {
    private String userId;
    private Integer storeId;
    private String storeName;    // 리액트에서 보여줄 지점명
    private String crrStrtDate;
    private String crrEndDate;
    private boolean authYn;
    private String status;       // "01", "02" 등 코드값
    private String statusName;   // "인증 대기", "인증 완료" 등 한글명

    // Entity -> DTO 변환 정적 메서드
    public static CrrHstrResponseDto from(CrrHstrEntity entity) {
        // 상태 코드에 따른 한글명 매핑
        String sName = "01".equals(entity.getStatus()) ? "인증 대기" :
                "02".equals(entity.getStatus()) ? "인증 완료" : "기타";

        return CrrHstrResponseDto.builder()
                .userId(entity.getUserId())
                .storeId(entity.getStoreId())
                // Fetch Join으로 가져온 StoreEntity에서 지점명 추출
                .storeName(entity.getStoreInfo() != null ? entity.getStoreInfo().getStoreNm() : "정보 없음")
                .crrStrtDate(entity.getCrrStrtDate())
                .crrEndDate(entity.getCrrEndDate())
                .authYn(entity.isAuthYn())
                .status(entity.getStatus())
                .statusName(sName)
                .build();
    }
}
