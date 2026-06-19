package com.tarakki.organization.serviceimpl;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.common.entity.Organization;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;
    private final RestClient restClient;

    public OrganizationServiceImpl(
        OrganizationRepository organizationRepository,
        ModelMapper mapper,
        RestClient restClient
    ) {
        this.organizationRepository = organizationRepository;
        this.mapper = mapper;
        this.restClient = restClient;
    }

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        if (fetchMember(organizationRequest.getOwnerId()) == null) {
            throw new OwnerIdNotFoundException(organizationRequest.getOwnerId());
        }

        Organization organization = mapper.map(organizationRequest, Organization.class);
        Organization savedOrganization = organizationRepository.save(organization);
        return mapper.map(savedOrganization, OrganizationDTO.class);
    }

    private MemberDTO fetchMember(UUID memberId) {
        try {
            return restClient.get()
                    .uri("/api/members/{memberId}", memberId)
                    .retrieve()
                    .body(MemberDTO.class);
        } catch (RestClientException exception) {
            return null;
        } catch (RuntimeException exception) {
            return null;
        }
    }
}
