package com.tarakki.organization.service;

import com.tarakki.common.dto.OrgMemberDTO;

public interface OrgMemberService {
    OrgMemberDTO addOrgMemberInfo(OrgMemberDTO orgMemberDto, Long OrgId);
}
