package com.tarakki.organization.repository;

import com.tarakki.organization.entity.OrgMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgMemberRepository extends JpaRepository<OrgMember, Long> {

}
