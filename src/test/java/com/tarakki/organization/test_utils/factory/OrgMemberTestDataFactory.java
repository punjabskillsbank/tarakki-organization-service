package com.tarakki.organization.test_utils.factory;

import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.entity.OrgMember;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.tarakki.organization.enums.MemberAccountStatus.ACCEPTED;
import static com.tarakki.organization.enums.MemberAccountStatus.PENDING;
import static com.tarakki.organization.enums.OrgMemberRole.ORG_ADMIN;
import static com.tarakki.organization.enums.OrgMemberRole.ORG_MEMBER;


public class OrgMemberTestDataFactory {

    public static final String ORG_MEMBER_EMAIL = "myEmail@gamil.com";

    public static OrgMemberDTO createOrgMemberDTO() {
        return OrgMemberDTO.builder()
                .orgId(9L)
                .memberId(UUID.randomUUID())
                .email(ORG_MEMBER_EMAIL)
                .memberAccountStatus(ACCEPTED)
                .orgMemberRole(ORG_ADMIN)
                .build();
    }

    public static OrgMember createOrgMember() {
        return OrgMember.builder()
                .orgMemberId(1L)
                .orgId(9L)
                .memberId(UUID.randomUUID())
                .memberAccountStatus(ACCEPTED)
                .orgMemberRole(ORG_ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static List<OrgMember> createOrgMemberList() {
        OrgMember admin = OrgMember.builder()
                .orgMemberId(1L)
                .orgId(9L)
                .memberId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .memberAccountStatus(ACCEPTED)
                .orgMemberRole(ORG_ADMIN)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        OrgMember member = OrgMember.builder()
                .orgMemberId(2L)
                .orgId(9L)
                .memberId(UUID.fromString("22222222-2222-2222-2222-222222222222"))
                .memberAccountStatus(PENDING)
                .orgMemberRole(ORG_MEMBER)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return List.of(admin, member);
    }

    public static List<OrgMemberDTO> createOrgMemberDTOList() {
        return createOrgMemberList().stream()
                .map(orgMember -> OrgMemberDTO.builder()
                        .orgMemberId(orgMember.getOrgMemberId())
                        .orgId(orgMember.getOrgId())
                        .memberId(orgMember.getMemberId())
                        .memberAccountStatus(orgMember.getMemberAccountStatus())
                        .orgMemberRole(orgMember.getOrgMemberRole())
                        .createdAt(orgMember.getCreatedAt())
                        .updatedAt(orgMember.getUpdatedAt())
                        .build())
                .toList();
    }

}
