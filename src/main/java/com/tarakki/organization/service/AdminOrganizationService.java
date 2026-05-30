package com.tarakki.organization.service;

import com.tarakki.organization.dto.AdminOrganizationDTO;

import java.util.List;

public interface AdminOrganizationService {
    List<AdminOrganizationDTO> getAllOrganizations();
}
