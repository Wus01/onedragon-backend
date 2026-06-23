package restapi.prac.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HiringBoardDTO {
    private Long hiringNo;
    private String userId;
    private String cfrmUserId;
    private List<Long> applyNos;

    public Long getHiringNo() {
        return hiringNo;
    }

    public void setHiringNo(Long hiringNo) {
        this.hiringNo = hiringNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCfrmUserId() {
        return cfrmUserId;
    }

    public void setCfrmUserId(String cfrmUserId) {
        this.cfrmUserId = cfrmUserId;
    }

    public List<Long> getApplyNos() {
        return applyNos;
    }

    public void setApplyNos(List<Long> applyNos) {
        this.applyNos = applyNos;
    }
}
