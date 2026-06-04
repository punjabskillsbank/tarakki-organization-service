package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Member;
import com.tarakki.common.dto.MemberDTO;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.repository.MemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminOrganizationServiceImplTest {

    @Mock
    private OrganizationRepository organizationRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AdminOrganizationServiceImpl adminOrganizationService;

    private AdminOrganizationDTO organizationDTO;
    private MemberDTO memberDTO;
    private Member member;
    private Map<String, Object> organizationDetails;
    private UUID ownerId;

    @BeforeEach
    void setup() {
        ownerId = UUID.randomUUID();

        memberDTO = OrganizationTestDataFactory.createMemberDTO(ownerId);
        member = OrganizationTestDataFactory.createMemberEntity(ownerId);

        organizationDTO =
                OrganizationTestDataFactory.createAdminOrganizationDTO(
                        1L,
                        ownerId,
                        3L
                );

        organizationDetails = new HashMap<>();
        organizationDetails.put("orgId", 1L);
        organizationDetails.put("orgName", "Tarakki Organization");
        organizationDetails.put("orgDesc", "desc");
        organizationDetails.put("ownerId", ownerId);
        organizationDetails.put("orgAddress", "example address");
        organizationDetails.put("orgCity", "chandigarh");
        organizationDetails.put("orgState", "Punjab");
        organizationDetails.put("orgPostalCode", "140003");
        organizationDetails.put("orgCountry", "India");
        organizationDetails.put("totalMemberCount", 3L);
    }

    @Test
    void getAllOrganizations_shouldReturnAdminOrganizationDTOList() {

        when(organizationRepository.findAllOrganizationsWithOwnerAndMemberCount())
                .thenReturn(List.of(organizationDetails));

        when(mapper.map(organizationDetails, AdminOrganizationDTO.class))
                .thenReturn(organizationDTO);

        when(memberRepository.findAllById(any()))
                .thenReturn(List.of(member));

        when(mapper.map(eq(member), eq(MemberDTO.class)))
                .thenReturn(memberDTO);

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

        verify(organizationRepository)
                .findAllOrganizationsWithOwnerAndMemberCount();

        verify(mapper)
                .map(organizationDetails, AdminOrganizationDTO.class);
    }
}