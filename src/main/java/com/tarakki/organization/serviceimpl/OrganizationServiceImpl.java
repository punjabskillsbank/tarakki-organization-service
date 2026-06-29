package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.OrganizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;
    private final MemberClient memberClient;

    @Override
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        memberClient.validateMemberExists(organizationRequest.getOwnerId());
        return saveOrganization(organizationRequest);
    }

    @Transactional
    protected OrganizationDTO saveOrganization(OrganizationDTO organizationRequest) {
        Organization organization = mapper.map(organizationRequest, Organization.class);
        Organization savedOrganization = organizationRepository.save(organization);
        return mapper.map(savedOrganization, OrganizationDTO.class);
    }

    @Override
    public OrganizationDTO getOrganizationById(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new OrganizationNotFoundException(organizationId));
        return mapper.map(organization, OrganizationDTO.class);
    }
}
