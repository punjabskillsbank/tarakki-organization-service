package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.exceptionhandling.MemberNotInOrganizationException;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;
    private final MemberClient memberClient;

    @Override
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        UUID ownerId = organizationRequest.getOwnerId();
        if (!memberClient.doesMemberExist(ownerId)) {
            throw new OwnerIdNotFoundException(ownerId);
        }
        return saveOrganization(organizationRequest);
    }


    protected OrganizationDTO saveOrganization(OrganizationDTO organizationRequest) {
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
}
