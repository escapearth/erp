package org.erp.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PrivilegeEntity {

    @Id
    @GeneratedValue
    @Column(name = "privilege_id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privilegeEntities")
    private Collection<RoleEntity> roleEntities;

    public PrivilegeEntity(String name) {
        this.name = name;
    }
}


