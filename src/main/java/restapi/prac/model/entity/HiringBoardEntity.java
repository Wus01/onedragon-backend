package restapi.prac.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hiring_board")
public class HiringBoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hiringNo;
    private String userId;
    private Long hiringCnt;
    private Long hiringLikeCnt;

    @Column(name = "del_yn")
    private String delYn = "N"; // 객체 생성 시점에 바로 'N'으로 초기화

    private String hiringSts;

    @CreationTimestamp // INSERT 시점에 현재 시간 자동 입력
    private String hiringDate;

    @UpdateTimestamp // UPDATE 시점에 현재 시간 자동 입력
    private String hiringUpdtDate;

    private String serviceType;
    private String workStartDate;
    private String workEndDate;
    private String negotiableYn;
    private String urgencyYn;
    private String hiringTitle;
    private String hiringText;
    private String payPerHour;

    @CreationTimestamp
    private String rgstDate;

    @UpdateTimestamp
    private String updtDate;

    private String rgstId;
    private String updtId;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정 (성능 최적화)
    @JoinColumn(name = "store_id") // DB의 hiring_board 테이블에 있는 외래 키 컬럼 이름
    private StoreEntity storeInfo;

    // HiringBoard.java 에 추가

    @OneToMany(mappedBy = "hiringBoardEntity") // ApplyInfo 엔티티에 있는 hiringBoard 필드에 의해 매핑됨
    private List<ApplyEntity> applyList = new ArrayList<>();

    public List<ApplyEntity> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<ApplyEntity> applyList) {
        this.applyList = applyList;
    }

    public StoreEntity getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreEntity storeInfo) {
        this.storeInfo = storeInfo;
    }

    public Long getHiringNo() {
        return hiringNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setHiringNo(Long hiringNo) {
        this.hiringNo = hiringNo;
    }

    public Long getHiringCnt() {
        return hiringCnt;
    }

    public void setHiringCnt(Long hiringCnt) {
        this.hiringCnt = hiringCnt;
    }

    public Long getHiringLikeCnt() {
        return hiringLikeCnt;
    }

    public void setHiringLikeCnt(Long hiringlikeCnt) {
        this.hiringLikeCnt = hiringlikeCnt;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getHiringSts() {
        return hiringSts;
    }

    public void setHiringSts(String hiringSts) {
        this.hiringSts = hiringSts;
    }

    public String getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(String hiringDate) {
        this.hiringDate = hiringDate;
    }

    public String getHiringUpdtDate() {
        return hiringUpdtDate;
    }

    public void setHiringUpdtDate(String hiringUpdtDate) {
        this.hiringUpdtDate = hiringUpdtDate;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(String workStartDate) {
        this.workStartDate = workStartDate;
    }

    public String getWorkEndDate() {
        return workEndDate;
    }

    public void setWorkEndDate(String workEndDate) {
        this.workEndDate = workEndDate;
    }

    public String getNegotiableYn() {
        return negotiableYn;
    }

    public void setNegotiableYn(String negotiableYn) {
        this.negotiableYn = negotiableYn;
    }

    public String getUrgencyYn() {
        return urgencyYn;
    }

    public void setUrgencyYn(String urgencyYn) {
        this.urgencyYn = urgencyYn;
    }

    public String getHiringTitle() {
        return hiringTitle;
    }

    public void setHiringTitle(String hiringTitle) {
        this.hiringTitle = hiringTitle;
    }

    public String getHiringText() {
        return hiringText;
    }

    public void setHiringText(String hiringText) {
        this.hiringText = hiringText;
    }

    public String getRgstDate() {
        return rgstDate;
    }

    public void setRgstDate(String rgstDate) {
        this.rgstDate = rgstDate;
    }

    public String getUpdtDate() {
        return updtDate;
    }

    public void setUpdtDate(String updtDate) {
        this.updtDate = updtDate;
    }

    public String getRgstId() {
        return rgstId;
    }

    public void setRgstId(String rgstId) {
        this.rgstId = rgstId;
    }

    public String getUpdtId() {
        return updtId;
    }

    public void setUpdtId(String updtId) {
        this.updtId = updtId;
    }

    public String getPayPerHour() {
        return payPerHour;
    }

    public void setPayPerHour(String payPerHour) {
        this.payPerHour = payPerHour;
    }
}
