package com.tarakki.organization.serviceimpl;

import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.AdminOrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminOrganizationServiceImpl implements AdminOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;

    @Override
    public List<AdminOrganizationDTO> getAllOrganizations() {
        return organizationRepository.findAllOrganizationsWithOwnerAndMemberCount().stream()
                .map(this::mapToAdminOrganizationDTO)
                .toList();
    }

    private AdminOrganizationDTO mapToAdminOrganizationDTO(Map<String, Object> organizationDetails) {
        return mapper.map(organizationDetails, AdminOrganizationDTO.class);
    }
}
