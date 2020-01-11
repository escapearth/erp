package org.erp.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.erp.model.common.CommonDateEntity;
import org.erp.model.member.MemberEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Builder
public class UserEntity extends CommonDateEntity {
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Size(min = 6, max = 100)
    @Column(length = 100)
    private String password;

    @Column(name = "user_name",nullable = false, length = 30)
    private String name;

    @Column(name = "email", nullable = false, length = 320)
    private String email;

    private boolean isEnabled;

    @OneToMany(mappedBy = "userEntity")
    private List<MemberEntity> memberEntities = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"))
    private Collection<RoleEntity> roleEntities;
}
