package restapi.prac.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "CRR_HSTR")
@IdClass(CrrHstrId.class)
@Data
public class CrrHstrVo extends BaseTimeEntity {

    @Id
    @Column(name = "USER_ID")
    private Integer userId;

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






}
