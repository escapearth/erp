package org.erp.model.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.erp.model.common.CommonDateEntity;
import org.erp.model.user.UserModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "MEMBERMODEL")
@EqualsAndHashCode(callSuper = false, of = "memberId")
@NoArgsConstructor
@Cacheable
@Getter
@Setter
@Builder
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberModel extends CommonDateEntity {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * Member Id
     */
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false, unique = true, length = 36)
    private String memberId;

    /**
     * UserModel
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
    @JoinColumn(name = "userId", updatable = false)
    private UserModel userModel;


    /**
     * Member 성명
     */
    @Column(length = 255)
    private String memberName;

    @Column(unique = true, nullable = false, length = 320)
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
    @Column(length = 255)
    private String memberAddress;

    /**
     * 조직원 상태
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1, columnDefinition ="char")
    protected MemberStatusType memberStatus;

    /**
     * 조직원 이미지 주소
     */
    @Deprecated
    @Column(length = 255)
    private String memberImage;

}

