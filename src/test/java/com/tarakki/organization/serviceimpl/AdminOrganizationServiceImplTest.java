package com.tarakki.organization.serviceimpl;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminOrganizationServiceImplTest {

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

    private AdminOrganizationServiceImpl adminOrganizationService;

    private AdminOrganizationDTO organizationDTO;
    private MemberDTO memberDTO;
    private Map<String, Object> organizationDetails;
    private UUID ownerId;

    @BeforeEach
    void setup() {
        ownerId = UUID.randomUUID();

        memberDTO = OrganizationTestDataFactory.createMemberDTO(ownerId);

        organizationDTO = OrganizationTestDataFactory.createAdminOrganizationDTO(1L, ownerId, 3L);

        organizationDetails = OrganizationTestDataFactory.createOrganizationDetails(1L, ownerId, 3L);
        adminOrganizationService = new AdminOrganizationServiceImpl(
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
    void getAllOrganizations_shouldReturnAdminOrganizationDTOList() {

        when(organizationRepository.findAllOrganizationsWithOwnerAndMemberCount())
                .thenReturn(List.of(organizationDetails));

        when(mapper.map(organizationDetails, AdminOrganizationDTO.class))
                .thenReturn(organizationDTO);

        when(responseSpec.body(MemberDTO.class)).thenReturn(memberDTO);

        List<AdminOrganizationDTO> result =
                adminOrganizationService.getAllOrganizations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(
                organizationDTO.getOrgName(),
                result.get(0).getOrgName()
        );
        assertEquals(
                organizationDTO.getTotalMemberCount(),
                result.get(0).getTotalMemberCount()
        );
        assertNotNull(result.get(0).getOwner());
        assertEquals(memberDTO.getMemberId(), result.get(0).getOwner().getMemberId());
        assertEquals(memberDTO.getEmail(), result.get(0).getOwner().getEmail());

        verify(organizationRepository)
                .findAllOrganizationsWithOwnerAndMemberCount();

        verify(mapper)
                .map(organizationDetails, AdminOrganizationDTO.class);
        verify(restClient).get();
        verify(requestHeadersUriSpec)
                .uri("/api/members/{memberId}", ownerId);
        verify(requestHeadersSpec).retrieve();
        verify(responseSpec).body(MemberDTO.class);
    }
}
