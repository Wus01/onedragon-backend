package restapi.prac.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import restapi.prac.model.entity.CrrHstrEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class UserInfoEntity {
    @Id
    private String userId;

    @Column(nullable = false)
    private String userNm;

    @Column(nullable = false)
    private String userPwd;

    @Column
    private String userEmail;

    @Column
    private String userPhoneNm;

    @Column(nullable = false, length = 1)
    private String userDelYn;

    @Column(nullable = false, length = 1)
    private String userType;

    @Column(nullable = false, length = 1)
    private String employerAuthYn;

    @Column(nullable = false)
    private Boolean agreementTerms;

    @Column
    private Date delDate;

    @Column(nullable = false)
    private Date rgstDate;

    @Column
    private Date updtDate;

    @Column(nullable = false)
    private String rgstId;

    @Column
    private String updtId;

    @Transient
    private String token;

    @OneToMany(mappedBy = "userInfo") // CrrHstr 엔티티에 있는 userInfo 필드와 매핑
    private List<CrrHstrEntity> crrHstrList = new ArrayList<>();
}