package restapi.prac.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Table(name = "CMN_CD_DTL")
public class CmnCdMstrEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String mstrCd;
    private String mstrCdNm;
    private String mstrCdDs;

    @Column(name = "del_yn")
    private String delYn = "N"; // 객체 생성 시점에 바로 'N'으로 초기화

    @CreationTimestamp
    private String rgstDate;

    @UpdateTimestamp
    private String updtDate;

    private String rgstId;
    private String updtId;



}
