package restapi.prac.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "apply_info")
public class ApplyEntity {
    @Id
    private Long applyNo;
    private String applyDate;
    private String applySucYn;
    private String rgstDate;
    private String rgstId;
    private String updtDate;
    private String updtId;

    // ApplyInfo.java (예시)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiring_no")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "applyList"})
    @JsonIgnore
    private HiringBoardEntity hiringBoardEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"applyList", "handler", "hibernateLazyInitializer"}) // applyList 참조 차단
    private UserInfoEntity userInfo; // 지원한 유저 정보

    public HiringBoardEntity getHiringBoardEntity() {
        return hiringBoardEntity;
    }

    public void setHiringBoardEntity(HiringBoardEntity hiringBoardEntity) {
        this.hiringBoardEntity = hiringBoardEntity;
    }

    public UserInfoEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.userInfo = userInfo;
    }

    public Long getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(Long applyNo) {
        this.applyNo = applyNo;
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
