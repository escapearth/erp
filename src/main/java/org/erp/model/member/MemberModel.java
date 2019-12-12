package org.erp.model.member;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MemberModel extends CommonDateEntity {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * Organization Id
     */
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "organizationId", updatable = false, nullable = false)
//    private OrganizationModel organizationModel;

    /**
     * Member Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private Long memberId;

    /**
     * UserModel msrl
     */
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = false)
    @JoinColumn(name = "msrl", updatable = false, nullable = false)
    private UserModel userModel;

    /**
     * Member 직위/직책 정보
     */
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "MemberJobTitle", //
//            joinColumns = { @JoinColumn(name = "memberId") }, //
//            inverseJoinColumns = { @JoinColumn(name = "jobTitleId") })
//    private List<HcmJobTitleModel> jobTitleModels;

    /**
     * Member 성명
     */
    @Column(length = 255)
    private String memberName;

    /**
     * Member E-mail 주소의 최대 길이는 320 이다. 64 + @ + 255 = 320 (1 = @)
     */
    @NaturalId
    @NotBlank
    @Email
    @Size(min = 6, max = 320)
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

