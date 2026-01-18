package restapi.prac.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // Entity 클래스들이 상속받아 이 필드들을 컬럼으로 인식하도록 설정
@EntityListeners(AuditingEntityListener.class) // JPA Auditing 리스너 적용
public class BaseTimeEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime rgstDate;

    private String rgstId;
    @LastModifiedDate
    private LocalDateTime updtDate;
    private String updtId;

    public void setRgstId(String rgstId) { this.rgstId = rgstId; }
    public void setUpdtId(String updtId) { this.updtId = updtId; }
}

