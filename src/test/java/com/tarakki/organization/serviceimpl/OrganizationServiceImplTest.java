package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Member;
import com.tarakki.common.enums.AccountStatus;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.common.entity.Organization;
import com.tarakki.organization.repository.MemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.projection.AdminOrganizationProjection;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrganizationServiceImplTest {
    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private OrganizationServiceImpl organizationService;

    private OrganizationDTO dto;
    private Organization organization;
    UUID ownerId;

    @BeforeEach
    void setup() {
        ownerId = UUID.randomUUID();
        dto = OrganizationTestDataFactory.createOrganizationDTO(1L, ownerId);
        organization = OrganizationTestDataFactory.createOrganizationEntity(1L, ownerId);
    }

    @Test
    void createWorkspace_shouldReturnSavedWorkspaceDTO() {
        when(memberRepository.findById(ownerId)).thenReturn(Optional.of(new Member()));
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

        verify(memberRepository).findById(ownerId);
        verify(mapper).map(any(OrganizationDTO.class), eq(Organization.class));
        verify(organizationRepository).save(any(Organization.class));
        verify(mapper).map(any(Organization.class), eq(OrganizationDTO.class));
    }

    @Test
    void createWorkspace_shouldThrowOwnerIdNotFoundExceptionWhenOwnerIdIsMissing() {
        when(memberRepository.findById(ownerId)).thenReturn(Optional.empty());

        OwnerIdNotFoundException exception = assertThrows(OwnerIdNotFoundException.class,
                () -> organizationService.createOrganization(dto));

        assertEquals("owner not found at given ownerId: " + ownerId, exception.getMessage());
        verify(memberRepository).findById(ownerId);
    }

    @Test
    void getAllOrganizationsForAdmin_shouldReturnAdminOrganizationDTOList() {
        AdminOrganizationProjection projection = mock(AdminOrganizationProjection.class);
        when(projection.getOrgId()).thenReturn(organization.getOrgId());
        when(projection.getOrgName()).thenReturn(organization.getOrgName());
        when(projection.getOrgDesc()).thenReturn(organization.getOrgDesc());
        when(projection.getOwnerId()).thenReturn(ownerId);
        when(projection.getOwnerFirstName()).thenReturn("Sahib");
        when(projection.getOwnerLastName()).thenReturn("Singh");
        when(projection.getOwnerEmail()).thenReturn("sahib@gmail.com");
        when(projection.getOwnerProfilePhotoS3Key()).thenReturn("profile-photo-key");
        when(projection.getOwnerAccountStatus()).thenReturn(AccountStatus.ACTIVE.name());
        when(projection.getOrgAddress()).thenReturn(organization.getOrgAddress());
        when(projection.getOrgCity()).thenReturn(organization.getOrgCity());
        when(projection.getOrgState()).thenReturn(organization.getOrgState());
        when(projection.getOrgPostalCode()).thenReturn(organization.getOrgPostalCode());
        when(projection.getOrgCountry()).thenReturn(organization.getOrgCountry());
        when(projection.getTotalMemberCount()).thenReturn(3L);
        when(organizationRepository.findAllOrganizationsWithOwnerAndMemberCount()).thenReturn(List.of(projection));

        List<AdminOrganizationDTO> result = organizationService.getAllOrganizationsForAdmin();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dto.getOrgName(), result.get(0).getOrgName());
        assertEquals(ownerId, result.get(0).getOwner().getMemberId());
        assertEquals("Sahib", result.get(0).getOwner().getFirstName());
        assertEquals("Singh", result.get(0).getOwner().getLastName());
        assertEquals("sahib@gmail.com", result.get(0).getOwner().getEmail());
        assertEquals("profile-photo-key", result.get(0).getOwner().getProfilePhotoS3Key());
        assertEquals(AccountStatus.ACTIVE, result.get(0).getOwner().getAccountStatus());
        assertEquals(3L, result.get(0).getTotalMemberCount());

        verify(organizationRepository).findAllOrganizationsWithOwnerAndMemberCount();
    }
}
