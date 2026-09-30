package com.tarakki.organization.service;


import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.dto.OrgMemberUpdateRequest;

import java.util.List;

public interface OrgMemberService {
    OrgMemberDTO addMemberToOrg(OrgMemberDTO orgMemberDto, Long OrgId);

    List<OrgMemberDTO> getMembersByOrgId(Long orgId);

    OrgMemberDTO updateOrgMember(Long orgMemberId, Long orgId, OrgMemberUpdateRequest updateRequest);

    void deleteOrgMember(Long orgMemberId, Long OrgId);
}
