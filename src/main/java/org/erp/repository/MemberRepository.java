package org.erp.repository;

import org.erp.model.member.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberModel, String> {

    Optional<MemberModel> findByMemberId(String memberId);
}
