package com.tarakki.organization.service;

import com.tarakki.organization.dto.OrganizationDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationService {
    OrganizationDTO createOrganization(OrganizationDTO organizationRequest);
}
