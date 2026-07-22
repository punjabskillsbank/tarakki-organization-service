package com.tarakki.organization.service;


import com.tarakki.organization.dto.OrgMemberDTO;

public interface OrgMemberService {
    OrgMemberDTO addMemberToOrg(OrgMemberDTO orgMemberDto, Long OrgId);
}
