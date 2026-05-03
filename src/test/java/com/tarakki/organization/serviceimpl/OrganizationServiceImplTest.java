package com.tarakki.organization.serviceimpl;

import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.common.entity.Organization;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceImplTest {
    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    private OrganizationDTO dto;
    private Organization organization;

    @BeforeEach
    void setup() {
        UUID owerId = UUID.randomUUID();
        dto = OrganizationTestDataFactory.createOrganizationDTO(1L, owerId);
        organization = OrganizationTestDataFactory.createMemberEntity(1L, owerId);
    }

    @Test
    void createWorkspace_shouldReturnSavedWorkspaceDTO() {
        when(mapper
                .map(any(OrganizationDTO.class), eq(Organization.class)))
                .thenReturn(organization);
        when(organizationRepository.save(any(Organization.class)))
                .thenReturn(organization);
        when(mapper
                .map(any(Organization.class), eq(OrganizationDTO.class)))
                .thenReturn(dto);

        OrganizationDTO result = organizationService.createOrganization(dto);

        assertNotNull(result);
        assertEquals(dto.getOrgName(), result.getOrgName());
        assertEquals(dto.getOrgDesc(), result.getOrgDesc());
        assertEquals(dto.getOwnerId(), result.getOwnerId());
        assertEquals(dto.getOrgCity(), result.getOrgCity());
        assertEquals(dto.getOrgAddress(), result.getOrgAddress());
        assertEquals(dto.getOrgPostalCode(), result.getOrgPostalCode());
        assertEquals(dto.getOrgState(), result.getOrgState());
        assertEquals(dto.getOrgCountry(), result.getOrgCountry());

        verify(mapper).map(any(OrganizationDTO.class), eq(Organization.class));
        verify(organizationRepository).save(any(Organization.class));
        verify(mapper).map(any(Organization.class), eq(OrganizationDTO.class));
    }
}
