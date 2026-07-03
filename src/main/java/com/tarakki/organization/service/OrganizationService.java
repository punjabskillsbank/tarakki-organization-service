package com.tarakki.organization.service;

import com.tarakki.organization.dto.OrganizationDTO;

import java.util.UUID;

public interface OrganizationService {
    OrganizationDTO createOrganization(OrganizationDTO organizationRequest);
    OrganizationDTO getOrganizationById(Long organizationId, UUID memberId);
}
