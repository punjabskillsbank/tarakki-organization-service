package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.OrganizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;


@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;
    private final RestClient restClient;

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        try {
            restClient.get()
                    .uri("/api/members/{memberId}", organizationRequest.getOwnerId())
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException exception) {
            throw new OwnerIdNotFoundException(organizationRequest.getOwnerId());
        }
        
        Organization organization = mapper.map(organizationRequest, Organization.class);
        Organization savedOrganization = organizationRepository.save(organization);
        return mapper.map(savedOrganization, OrganizationDTO.class);
    }

    @Override
    public OrganizationDTO getOrganizationById(Long organizationId) {
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new com.tarakki.organization.exceptionhandling.OrganizationNotFoundException(organizationId));
        return mapper.map(organization, OrganizationDTO.class);
    }
}
