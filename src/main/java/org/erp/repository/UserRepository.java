package org.erp.repository;

import org.erp.model.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, String> {

    Optional<UserModel> findByUid(String email);
}
