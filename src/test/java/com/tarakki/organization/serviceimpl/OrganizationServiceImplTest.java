package com.tarakki.organization.serviceimpl;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.common.entity.Organization;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private OrganizationServiceImpl organizationService;

    private OrganizationDTO dto;
    private Organization organization;
    private UUID ownerId;

    @BeforeEach
    void setup() {
        ownerId = UUID.randomUUID();
        dto = OrganizationTestDataFactory.createOrganizationDTO(1L, ownerId);
        organization = OrganizationTestDataFactory.createOrganizationEntity(1L, ownerId);
        organizationService = new OrganizationServiceImpl(
                organizationRepository,
                mapper,
                restClient
        );

        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/api/members/{memberId}", ownerId))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
    }

    @Test
    void createOrganization_shouldReturnSavedOrganizationDTO() {
        MemberDTO memberDTO = OrganizationTestDataFactory.createMemberDTO(ownerId);

        when(responseSpec.body(MemberDTO.class)).thenReturn(memberDTO);
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

        verify(restClient).get();
        verify(requestHeadersUriSpec)
                .uri("/api/members/{memberId}", ownerId);
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).body(MemberDTO.class);
        verify(mapper).map(any(OrganizationDTO.class), eq(Organization.class));
        verify(organizationRepository).save(any(Organization.class));
        verify(mapper).map(any(Organization.class), eq(OrganizationDTO.class));
    }

    @Test
    void createOrganization_shouldThrowOwnerIdNotFoundExceptionWhenOwnerIdIsMissing() {
        when(responseSpec.body(MemberDTO.class)).thenReturn(null);

        OwnerIdNotFoundException exception = assertThrows(OwnerIdNotFoundException.class,
                () -> organizationService.createOrganization(dto));

        assertEquals("owner not found at given ownerId: " + ownerId, exception.getMessage());
        verify(restClient).get();
        verify(requestHeadersUriSpec)
                .uri("/api/members/{memberId}", ownerId);
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).body(MemberDTO.class);
    }
}
