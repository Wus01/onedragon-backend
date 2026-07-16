package restapi.prac.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restapi.prac.model.CrrHstrId;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CRR_HSTR")
//@IdClass(CrrHstrId.class)
@Data
@Builder // 💡 빌더 패턴 사용 가능하게 함
@NoArgsConstructor // 💡 JPA 필수 (기본 생성자)
@AllArgsConstructor // 💡 @Builder 사용 시 모든 필드를 받는 생성자 필요
public class CrrHstrEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CRR_HSTR_NO")
    private Integer crrHstrNo;
//    @Id
    @Column(name = "USER_ID")
    private String userId;

//    @Id
    @Column(name = "STORE_ID")
    private Integer storeId;

    @Column(name = "CRR_STRT_DATE")
    private String crrStrtDate;

    @Column(name = "CRR_END_DATE")
    private String crrEndDate;

    @Column(name = "AUTH_YN")
    private boolean authYn = false;

    @Column(name = "RGST_ID")
    private String rgstId;

    @Column(name = "UPDT_ID")
    private String updtId;

    @Column(name = "DEL_YN")
    private boolean delYn = false; // 기본값은 false(삭제 안됨)

    @Column(length = 2)
    private String status = "01"; // 자바 필드에서 초기화하는 것이 가장 안전합니다.

    @Column(name="UPDT_DATE")
    private LocalDateTime updtDate;


    // 1. UserInfo와의 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false) // ✨ 수정: 읽기 전용으로 설정
    @JsonIgnore
    private UserInfoEntity userInfo;

    // 2. StoreInfo와의 연결
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", insertable = false, updatable = false) // ✨ 수정: 읽기 전용으로 설정
    private StoreEntity storeInfo;



}
