package com.tarakki.organization.test_utils.factory;

import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.entity.Organization;

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

    public static Organization createMemberEntity(Long orgId, UUID ownerId) {
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

}
