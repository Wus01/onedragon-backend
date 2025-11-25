package restapi.prac.model;

import jakarta.persistence.*;
import restapi.prac.controller.StoreInfoController;

@Entity
@Table(name = "hiring_board")
public class HiringBoard {
    @Id
    private Long hiringNo;
//    private Long storeId;
    private Long hiringCnt;
    private Long hiringLikeCnt;
    private String delYn;
    private String hiringSts;
    private String hiringDate;
    private String hiringUpdtDate;
    private String serviceType;
    private String workStartDate;
    private String workEndDate;
    private String negotiableYn;
    private String urgencyYn;
    private String hiringTitle;
    private String hiringText;
    private String rgstDate;
    private String updtDate;
    private String rgstId;
    private String updtId;

    @ManyToOne(fetch = FetchType.LAZY) // 지연 로딩 설정 (성능 최적화)
    @JoinColumn(name = "store_id") // DB의 hiring_board 테이블에 있는 외래 키 컬럼 이름
    private StoreDO storeInfo;

    public StoreDO getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreDO storeInfo) {
        this.storeInfo = storeInfo;
    }

    public Long getHiringNo() {
        return hiringNo;
    }

    public void setHiringNo(Long hiringNo) {
        this.hiringNo = hiringNo;
    }

//    public Long getStoreId() {
//        return storeId;
//    }
//
//    public void setStoreId(Long storeId) {
//        this.storeId = storeId;
//    }

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
}
