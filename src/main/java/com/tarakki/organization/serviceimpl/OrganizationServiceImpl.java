package com.tarakki.organization.serviceimpl;

import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.common.entity.Organization;
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

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        Organization organization = mapper.map(organizationRequest, Organization.class);
        Organization savedOrganization = organizationRepository.save(organization);
        return mapper.map(savedOrganization, OrganizationDTO.class);
    }
}
