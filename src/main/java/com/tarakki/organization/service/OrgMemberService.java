package com.tarakki.organization.service;


import com.tarakki.organization.dto.OrgMemberDTO;

import java.util.List;

public interface OrgMemberService {
    OrgMemberDTO addMemberToOrg(OrgMemberDTO orgMemberDto, Long OrgId);

    List<OrgMemberDTO> getMembersByOrgId(Long orgId);

    void deleteOrgMember(Long orgMemberId, Long OrgId);
}
