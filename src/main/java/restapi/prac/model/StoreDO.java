package restapi.prac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_info")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})          // JSON 직렬화 문제 해결
public class StoreDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeId;

    private String storeNm;
    private String storeAddr;
    private String storeCtgry;
    private String rgstId;
    private LocalDateTime rgstDate;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreNm() {
        return storeNm;
    }

    public void setStoreNm(String storeNm) {
        this.storeNm = storeNm;
    }

    public String getStoreAddr() {
        return storeAddr;
    }

    public void setStoreAddr(String storeAddr) {
        this.storeAddr = storeAddr;
    }

    public String getStoreCtgry() {
        return storeCtgry;
    }

    public void setStoreCtgry(String storeCtgry) {
        this.storeCtgry = storeCtgry;
    }

    public String getRgstId() {
        return rgstId;
    }

    public void setRgstId(String rgstId) {
        this.rgstId = rgstId;
    }

    public LocalDateTime getRgstDate() {
        return rgstDate;
    }

    public void setRgstDate(LocalDateTime rgstDate) {
        this.rgstDate = rgstDate;
    }

    // insert 시점에만 호출되는 메소드
    @PrePersist
    public void prePersist() {
        this.rgstDate = LocalDateTime.now();

        // 우선 사용자 id 하드코딩
        if (this.rgstId == null) {
            this.rgstId = "SYSTEM";
        }
    }
}
