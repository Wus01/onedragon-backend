package restapi.prac.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class UserInfo {
    @Id
    private String userId;

    @Column(nullable = false)
    private String userNm;

    @Column(nullable = false)
    private String userPwd;

    @Column
    private String userEmail;

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
}