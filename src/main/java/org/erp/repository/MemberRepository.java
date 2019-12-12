package org.erp.repository;

import org.erp.model.member.MemberModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberModel, Long> {
}
