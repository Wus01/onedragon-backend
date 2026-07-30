package restapi.prac.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "notification")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert // insert 시 null인 필드는 제외하고 insert (그럼 default값으로 세팅돼서 들어감)
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notiNo;
    @Column(name = "rcvr_id", nullable = false)
    private String rcvrId;
    private Long applyNo;
    private String notiContent;
    private String readYn;
    private String targetUrl;
    private String rgstDate;
    private String rgstId;
    private String updtDate;
    private String updtId;


    @Builder
    public NotificationEntity(String rcvrId, String notiContent, String targetUrl) {
        this.rcvrId = rcvrId;
        this.notiContent = notiContent;
        this.targetUrl = targetUrl;
    }
}
