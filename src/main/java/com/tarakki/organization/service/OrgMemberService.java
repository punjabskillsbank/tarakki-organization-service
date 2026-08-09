package com.tarakki.organization.service;


import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.dto.OrgMemberRequestDTO;

import java.util.List;

public interface OrgMemberService {
    OrgMemberDTO addMemberToOrg(OrgMemberRequestDTO orgMemberRequestDto, Long OrgId);

    List<OrgMemberDTO> getMembersByOrgId(Long orgId);
}
