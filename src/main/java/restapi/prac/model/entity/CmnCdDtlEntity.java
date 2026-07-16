package restapi.prac.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "CMN_CD_DTL")

public class CmnCdDtlEntity {
    @Id
    private String dtlCd;
    @Id
    private String mstrCd;
    private String dtlCdNm;
    private String dtlCdDs;

    @Column(name = "del_yn")
    private String delYn = "N"; // 객체 생성 시점에 바로 'N'으로 초기화

    @CreationTimestamp
    private String rgstDate;

    @UpdateTimestamp
    private String updtDate;

    private String rgstId;
    private String updtId;

    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public class CmnCdDtlId implements Serializable {
        private String mstrCd;
        private String dtlCd;
    }

}
