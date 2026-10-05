package com.tarakki.organization.repository;

import com.tarakki.organization.entity.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrgMemberRepository extends JpaRepository<OrgMember, Long> {

    List<OrgMember> findByOrgId(Long orgId);

    Optional<OrgMember> findByOrgMemberIdAndOrgId(Long orgMemberId, Long orgId);

    void deleteOrgMemberByOrgMemberId(Long orgMemberId ,Long orgId);
}
