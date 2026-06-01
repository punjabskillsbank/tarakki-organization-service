package com.tarakki.organization.test_utils.factory;

import com.tarakki.common.entity.Organization;
import com.tarakki.common.entity.Member;
import com.tarakki.common.enums.AccountStatus;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.dto.OrganizationDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrganizationTestDataFactory {
    public static OrganizationDTO createOrganizationDTO(Long orgId, UUID ownerId) {
        return OrganizationDTO.builder()
                .orgName("ngo")
                .ownerId(ownerId)
                .orgDesc("desc")
                .orgAddress("example address")
                .orgCity("chandigarh")
                .orgPostalCode("140003")
                .orgState("Punjab")
                .orgCountry("India")
                .build();
    }

    public static Organization createOrganizationEntity(Long orgId, UUID ownerId) {
        Organization organization = new Organization();
        organization.setOrgId(orgId);
        organization.setOrgName("ngo");
        organization.setOwnerId(ownerId);
        organization.setOrgDesc("desc");
        organization.setOrgAddress("example address");
        organization.setOrgCity("chandigarh");
        organization.setOrgState("Punjab");
        organization.setOrgCountry("India");
        organization.setOrgPostalCode("140003");
        organization.setCreatedAt(LocalDateTime.now());
        organization.setUpdatedAt(LocalDateTime.now());
        return organization;
    }

    public static Member createMemberEntity(UUID memberId) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setFirstName("John");
        member.setLastName("Doe");
        member.setEmail("john.doe@example.com");
        member.setProfilePhotoS3Key("photo.jpg");
        member.setAccountStatus(AccountStatus.ACTIVE);
        return member;
    }

    public static AdminOrganizationDTO createAdminOrganizationDTO(Long orgId, UUID ownerId, Long totalMemberCount) {
        return AdminOrganizationDTO.builder()
                .orgId(orgId)
                .orgName("ngo")
                .owner(createMemberEntity(ownerId))
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
