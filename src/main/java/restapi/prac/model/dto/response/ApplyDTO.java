package restapi.prac.model.dto.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApplyDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyNo;
    private String applyDate;
    private String applySucYn;
    private String rgstDate;
    private String rgstId;
    private String updtDate;
    private String updtId;
    private Long hiringNo;
    private String applyUserId;

}
