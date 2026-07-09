package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.tarakki.organization.exceptionhandling.MemberNotInOrganizationException;
import com.tarakki.common.dto.MemberDTO;
import org.springframework.web.client.RestClientException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceImplTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private MemberClient memberClient;

    @Mock
    private ModelMapper mapper;

    private OrganizationServiceImpl organizationService;

    private OrganizationDTO dto;
    private Organization organization;
    private UUID ownerId;
    private Long orgId;

    @BeforeEach
    void setup() {
        ownerId = UUID.randomUUID();
        orgId = OrganizationTestDataFactory.createOrganizationId();
        dto = OrganizationTestDataFactory.createOrganizationDTO(orgId, ownerId);
        organization = OrganizationTestDataFactory.createOrganizationEntity(orgId, ownerId);
        organizationService = new OrganizationServiceImpl(
                organizationRepository,
                mapper,
                memberClient
        );
    }

    @Test
    void createOrganization_shouldReturnSavedOrganizationDTO() {
        when(memberClient.getMemberById(ownerId)).thenReturn(new MemberDTO());
        when(mapper.map(any(OrganizationDTO.class), eq(Organization.class)))
                .thenReturn(organization);
        when(organizationRepository.save(any(Organization.class)))
                .thenReturn(organization);
        when(mapper.map(any(Organization.class), eq(OrganizationDTO.class)))
                .thenReturn(dto);

        OrganizationDTO result = organizationService.createOrganization(dto);

        assertNotNull(result);
        assertEquals(dto.getOrgId(), result.getOrgId());
        assertEquals(dto.getOrgName(), result.getOrgName());
        assertEquals(dto.getOrgDesc(), result.getOrgDesc());
        assertEquals(dto.getOwnerId(), result.getOwnerId());
        assertEquals(dto.getOrgCity(), result.getOrgCity());
        assertEquals(dto.getOrgAddress(), result.getOrgAddress());
        assertEquals(dto.getOrgPostalCode(), result.getOrgPostalCode());
        assertEquals(dto.getOrgState(), result.getOrgState());
        assertEquals(dto.getOrgCountry(), result.getOrgCountry());

        verify(memberClient).getMemberById(ownerId);
        verify(mapper).map(any(OrganizationDTO.class), eq(Organization.class));
        verify(organizationRepository).save(any(Organization.class));
        verify(mapper).map(any(Organization.class), eq(OrganizationDTO.class));
    }

    @Test
    void createOrganization_shouldThrowOwnerIdNotFoundExceptionWhenOwnerIdIsMissing() {
        when(memberClient.getMemberById(ownerId)).thenThrow(new RestClientException("Member not found"));

        OwnerIdNotFoundException exception = assertThrows(OwnerIdNotFoundException.class,
                () -> organizationService.createOrganization(dto));

        assertEquals("owner not found at given ownerId: " + ownerId, exception.getMessage());
        verify(memberClient).getMemberById(ownerId);
    }

    @Test
    void getOrganizationById_shouldReturnOrganizationDTO() {
        when(organizationRepository.findById(orgId)).thenReturn(Optional.of(organization));
        when(organizationRepository.existsMemberInOrganization(orgId, ownerId)).thenReturn(true);
        when(mapper.map(organization, OrganizationDTO.class)).thenReturn(dto);

        OrganizationDTO result = organizationService.getOrganizationById(orgId, ownerId);

        assertNotNull(result);
        assertEquals(dto.getOrgId(), result.getOrgId());
        assertEquals(dto.getOrgName(), result.getOrgName());

        verify(organizationRepository).findById(orgId);
        verify(organizationRepository).existsMemberInOrganization(orgId, ownerId);
        verify(mapper).map(organization, OrganizationDTO.class);
    }

    @Test
    void getOrganizationById_shouldThrowOrganizationNotFoundExceptionWhenOrganizationNotFound() {
        when(organizationRepository.findById(orgId)).thenReturn(Optional.empty());

        OrganizationNotFoundException exception = assertThrows(OrganizationNotFoundException.class,
                () -> organizationService.getOrganizationById(orgId, ownerId));

        assertEquals("Organization with ID " + orgId + " not found", exception.getMessage());
        verify(organizationRepository).findById(orgId);
    }

    @Test
    void getOrganizationById_shouldThrowMemberNotInOrganizationExceptionWhenMemberNotInOrg() {
        when(organizationRepository.findById(orgId)).thenReturn(Optional.of(organization));
        when(organizationRepository.existsMemberInOrganization(orgId, ownerId)).thenReturn(false);

        MemberNotInOrganizationException exception = assertThrows(MemberNotInOrganizationException.class,
                () -> organizationService.getOrganizationById(orgId, ownerId));

        assertEquals("Member " + ownerId + " does not belong to organization " + orgId, exception.getMessage());
        verify(organizationRepository).findById(orgId);
        verify(organizationRepository).existsMemberInOrganization(orgId, ownerId);
    }

    @Test
    void existsMemberInOrganization_shouldReturnTrueWhenMemberExists() {
        when(organizationRepository.existsMemberInOrganization(orgId, ownerId)).thenReturn(true);

        boolean result = organizationService.existsMemberInOrganization(orgId, ownerId);

        assertTrue(result);
        verify(organizationRepository).existsMemberInOrganization(orgId, ownerId);
    }

    @Test
    void existsMemberInOrganization_shouldReturnFalseWhenMemberDoesNotExist() {
        when(organizationRepository.existsMemberInOrganization(orgId, ownerId)).thenReturn(false);

        boolean result = organizationService.existsMemberInOrganization(orgId, ownerId);

        assertFalse(result);
        verify(organizationRepository).existsMemberInOrganization(orgId, ownerId);
    }
}
