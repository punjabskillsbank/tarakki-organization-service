package com.tarakki.organization.test_utils.factory;

import com.tarakki.common.entity.Organization;
import com.tarakki.common.entity.Member;
import com.tarakki.common.dto.MemberDTO;
import com.tarakki.common.enums.AccountStatus;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.dto.OrganizationDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
