package com.tarakki.organization.test_utils.factory;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.common.enums.AccountStatus;
import com.tarakki.organization.dto.AdminOrganizationDTO;

import java.util.UUID;

public class AdminOrganizationTestDataFactory {

    public static MemberDTO createMemberDTO(UUID memberId) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberId(memberId);
        memberDTO.setFirstName("John");
        memberDTO.setLastName("Doe");
        memberDTO.setEmail("john.doe@example.com");
        memberDTO.setProfilePhotoS3Key("photo.jpg");
        memberDTO.setAccountStatus(AccountStatus.ACTIVE);
        return memberDTO;
    }

    public static AdminOrganizationDTO createAdminOrganizationDTO(Long orgId, UUID ownerId, Long totalMemberCount) {
        return AdminOrganizationDTO.builder()
                .orgId(orgId)
                .orgName("ngo")
                .owner(createMemberDTO(ownerId))
                .orgDesc("desc")
                .orgAddress("example address")
                .orgCity("chandigarh")
                .orgPostalCode("140003")
                .orgState("Punjab")
                .orgCountry("India")
                .totalMemberCount(totalMemberCount)
                .build();
    }
}