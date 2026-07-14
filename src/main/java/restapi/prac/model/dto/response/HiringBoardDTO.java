package restapi.prac.model.dto.response;

import lombok.Getter;
import lombok.Setter;
import restapi.prac.model.entity.HiringBoardEntity;

import java.util.List;

@Getter
@Setter
public class HiringBoardDTO {
    private Long hiringNo;
    private String userId;
    private String cfrmUserId;
    private List<Long> applyNos;
    private String hiringStsNm;
    private String hiringSts;
    private String hiringTitle;
    private String hiringText;
    private String rgstDate;
    private String rgstId;
    private String workStartDate;
    private String workEndDate;
    private String storeNm;

    public HiringBoardDTO(HiringBoardEntity entity, String dtlCdNm) {
        this.hiringNo = entity.getHiringNo();
        this.hiringTitle = entity.getHiringTitle();
        this.hiringSts = entity.getHiringSts();
        this.hiringStsNm = dtlCdNm;
        this.hiringText = entity.getHiringText();
        this.storeNm = entity.getStoreInfo() != null ? entity.getStoreInfo().getStoreNm() : "";
        this.rgstId = entity.getRgstId();
        this.rgstDate = entity.getRgstDate();
    }

}
