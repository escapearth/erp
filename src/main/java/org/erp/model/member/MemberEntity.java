package org.erp.model.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.erp.model.common.CommonDateEntity;
import org.erp.model.user.UserEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class MemberEntity extends CommonDateEntity {

    /**
     * Member Id
     */
    @Id @GeneratedValue
    @Column(name = "member_id", columnDefinition = "CHAR(36)", updatable = false, length = 36)
    private String memberId;

    /**
     * UserEntity
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private UserEntity userEntity;

    /**
     * Member 성명
     */
    @Column(name = "member_name")
    private String memberName;

    @Column(nullable = false, length = 320)
    private String memberEmail;

    /**
     * Member 개인 전화번호 000-0000-0000 형태
     */
    @Column(length = 100)
    private String memberTelNo;

    /**
     * Member 회사 전화번호 000-0000-0000 형태
     */
    @Column(length = 100)
    private String memberTelNoOffice;

    /**
     * Member 주소
     */
    @Column
    private String memberAddress;

    /**
     * 조직원 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1, columnDefinition ="CHAR")
    protected MemberStatusType memberStatus;

    /**
     * 조직원 이미지 주소
     */
    @Deprecated
    @Column
    private String memberImage;

}

