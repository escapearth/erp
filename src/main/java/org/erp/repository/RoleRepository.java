package org.erp.repository;

import org.erp.model.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    RoleEntity findByName(String name);
}
