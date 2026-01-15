package restapi.prac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CRR_HSTR")
@IdClass(CrrHstrId.class)
@Data
public class CrrHstrVo extends BaseTimeEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Id
    @Column(name = "STORE_ID")
    private Integer storeId;

    @Column(name = "CRR_STRT_DATE")
    private String crrStrtDate;

    @Column(name = "CRR_END_DATE")
    private String crrEndDate;

    @Column(name = "AUTH_YN")
    private boolean authYn;

    @Column(name = "RGST_ID")
    private String rgstId;

    @Column(name = "UPDT_ID")
    private String updtId;

    @Column(name = "DEL_YN")
    private boolean delYn;


    // 1. UserInfo와의 연결 (사용자 1명 : 경력 여러개)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // DB의 user_id 컬럼과 매핑
    @JsonIgnore
    private UserInfo userInfo;

    // 2. StoreInfo와의 연결 (매장 1개 : 경력 여러개)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id") // DB의 store_id 컬럼과 매핑
    private StoreEntity storeInfo;



}
