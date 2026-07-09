package com.tarakki.organization.service;

import com.tarakki.organization.dto.OrgMemberDto;

public interface OrgMemberService {
    OrgMemberDto addOrgMemberInfo(OrgMemberDto orgMemberDto, Long OrgId);
}
