package com.tarakki.organization.test_utils.factory;

import com.tarakki.organization.dto.OrgMemberDto;
import com.tarakki.organization.entity.OrgMember;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.tarakki.organization.enums.OrgMemberRole.ORG_ADMIN;
import static com.tarakki.organization.enums.MemberAccountStatus.ACCEPTED;

public class OrgMemberTestDataFactory {

    public static OrgMemberDto createOrgMemberDTO() {
        return OrgMemberDto.builder()
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
