package com.tarakki.organization.test_utils.factory;

import com.tarakki.common.entity.Organization;
import com.tarakki.common.enums.AccountStatus;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.dto.OrganizationOwnerDTO;

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

    public static AdminOrganizationDTO createAdminOrganizationDTO(Long orgId, UUID ownerId, Long totalMemberCount) {
        return AdminOrganizationDTO.builder()
                .orgId(orgId)
                .orgName("ngo")
                .owner(OrganizationOwnerDTO.builder()
                        .memberId(ownerId)
                        .firstName("Sahib")
                        .lastName("Singh")
                        .email("sahib@gmail.com")
                        .profilePhotoS3Key("profile-photo-key")
                        .accountStatus(AccountStatus.ACTIVE)
                        .build())
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
