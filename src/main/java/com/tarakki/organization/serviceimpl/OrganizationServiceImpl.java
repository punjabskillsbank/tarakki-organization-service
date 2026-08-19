package com.tarakki.organization.serviceimpl;

import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.enums.MemberAccountStatus;
import com.tarakki.organization.enums.OrgMemberRole;
import com.tarakki.common.entity.Organization;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.entity.OrgMember;
import com.tarakki.organization.exceptionhandling.MemberNotInOrganizationException;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.repository.OrgMemberRepository;
import com.tarakki.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;
    private final MemberClient memberClient;
    private final OrgMemberRepository orgMemberRepository;

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        UUID ownerId = organizationRequest.getOwnerId();
        try {
            memberClient.getMemberById(ownerId);
        } catch (RestClientException e) {
            throw new OwnerIdNotFoundException(ownerId);
        }
        OrganizationDTO savedOrganizationDto = saveOrganization(organizationRequest);

        saveOrgMember(savedOrganizationDto.getOrgId(), ownerId);

        return savedOrganizationDto;
    }

    private void saveOrgMember(Long orgId, UUID memberId) {
        OrgMemberDTO orgMemberDTO = OrgMemberDTO.builder()
                .orgId(orgId)
                .memberId(memberId)
                .memberAccountStatus(MemberAccountStatus.ACCEPTED)
                .orgMemberRole(OrgMemberRole.ORG_ADMIN)
                .build();
        OrgMember orgMember = mapper.map(orgMemberDTO, OrgMember.class);
        orgMemberRepository.save(orgMember);
    }

    private OrganizationDTO saveOrganization(OrganizationDTO organizationRequest) {
        Organization organization = mapper.map(organizationRequest, Organization.class);
        Organization savedOrganization = organizationRepository.save(organization);
        return mapper.map(savedOrganization, OrganizationDTO.class);
    }

    @Override
    public OrganizationDTO getOrganizationById(Long organizationId, UUID memberId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId));
        if (!organizationRepository.existsMemberInOrganization(organizationId, memberId)) {
            throw new MemberNotInOrganizationException(memberId, organizationId);
        }
        return mapper.map(organization, OrganizationDTO.class);
    }

    @Override
    public boolean existsMemberInOrganization(Long organizationId, UUID memberId) {
        return organizationRepository.existsMemberInOrganization(organizationId, memberId);
    }

    @Override
    @Transactional
    public OrganizationDTO updateOrganization(Long organizationId, OrganizationDTO organizationRequest) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId));

        mapper.map(organizationRequest, organization);
        Organization updatedOrganization = organizationRepository.save(organization);

        return mapper.map(updatedOrganization, OrganizationDTO.class);
    }
}
