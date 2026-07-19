package com.tarakki.organization.test_utils.factory;

import com.tarakki.common.dto.OrgMemberDTO;
import com.tarakki.organization.entity.OrgMember;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.tarakki.common.enums.MemberAccountStatus.ACCEPTED;
import static com.tarakki.common.enums.OrgMemberRole.ORG_ADMIN;


public class OrgMemberTestDataFactory {

    public static OrgMemberDTO createOrgMemberDTO() {
        return OrgMemberDTO.builder()
                .orgId(9L)
                .memberId(UUID.randomUUID())
                .email("myEmail@gamil.com")
                .memberAccountStatus(ACCEPTED)
                .orgMemberRole(ORG_ADMIN)
                .build();
    }

    public static OrgMember createOrgMember() {
        return OrgMember.builder()
                .orgMemberId(1L)
                .orgId(9L)
                .memberId(UUID.randomUUID())
                .email("myEmail@gamil.com")
                .memberAccountStatus(ACCEPTED)
                .orgMemberRole(ORG_ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


}
