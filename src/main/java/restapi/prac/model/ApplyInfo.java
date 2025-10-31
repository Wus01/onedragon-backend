package restapi.prac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ApplyInfo {
    @Id
    private Long applyNo;
    private Long hiringNo;
    private String userId;
    private String applyDate;
    private String applySucYn;
    private String rgstDate;
    private String rgstId;
    private String updtDate;
    private String updtId;

    public Long getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(Long applyNo) {
        this.applyNo = applyNo;
    }

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

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplySucYn() {
        return applySucYn;
    }

    public void setApplySucYn(String applySucYn) {
        this.applySucYn = applySucYn;
    }

    public String getRgstDate() {
        return rgstDate;
    }

    public void setRgstDate(String rgstDate) {
        this.rgstDate = rgstDate;
    }

    public String getRgstId() {
        return rgstId;
    }

    public void setRgstId(String rgstId) {
        this.rgstId = rgstId;
    }

    public String getUpdtDate() {
        return updtDate;
    }

    public void setUpdtDate(String updtDate) {
        this.updtDate = updtDate;
    }

    public String getUpdtId() {
        return updtId;
    }

    public void setUpdtId(String updtId) {
        this.updtId = updtId;
    }
}
