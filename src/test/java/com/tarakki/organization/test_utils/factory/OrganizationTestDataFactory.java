package com.tarakki.organization.test_utils.factory;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.dto.OrganizationDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrganizationTestDataFactory {
    public static Long createOrganizationId() {
        return 1L;
    }

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
    public static Map<String, Object> createOrganizationDetails(Long orgId, UUID ownerId, Long totalMemberCount) {
        Map<String, Object> organizationDetails = new HashMap<>();
        organizationDetails.put("orgId", orgId);
        organizationDetails.put("orgName", "Tarakki Organization");
        organizationDetails.put("orgDesc", "desc");
        organizationDetails.put("ownerId", ownerId);
        organizationDetails.put("orgAddress", "example address");
        organizationDetails.put("orgCity", "chandigarh");
        organizationDetails.put("orgState", "Punjab");
        organizationDetails.put("orgPostalCode", "140003");
        organizationDetails.put("orgCountry", "India");
        organizationDetails.put("totalMemberCount", totalMemberCount);
        return organizationDetails;
    }


}
