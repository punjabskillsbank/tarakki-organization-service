package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Member;
import com.tarakki.member.dto.MemberDTO;
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
    private Map<String, Object> organizationDetails;
    private UUID ownerId;
    private Member ownerMember;
    private MemberDTO ownerMemberDTO;

    @BeforeEach
    void setup() {
        ownerId = UUID.randomUUID();
        organizationDTO = OrganizationTestDataFactory.createAdminOrganizationDTO(1L, ownerId, 3L);
        organizationDetails = new HashMap<>();
        organizationDetails.put("totalMemberCount", 3L);
        organizationDetails.put("ownerId", ownerId);

        ownerMember = new Member();
        ownerMember.setMemberId(ownerId);
        ownerMemberDTO = OrganizationTestDataFactory.createMemberDTO(ownerId);
    }

    @Test
    void getAllOrganizations_shouldReturnAdminOrganizationDTOList() {
        when(organizationRepository.findAllOrganizationsWithOwnerAndMemberCount())
                .thenReturn(List.of(organizationDetails));
        when(memberRepository.findAllById(List.of(ownerId))).thenReturn(List.of(ownerMember));
        when(mapper.map(organizationDetails, AdminOrganizationDTO.class)).thenReturn(organizationDTO);
        when(mapper.map(ownerMember, MemberDTO.class)).thenReturn(ownerMemberDTO);

        List<AdminOrganizationDTO> result = adminOrganizationService.getAllOrganizations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(organizationDTO.getOrgName(), result.get(0).getOrgName());
        assertEquals(ownerMemberDTO, result.get(0).getOwner());
        assertEquals(3L, result.get(0).getTotalMemberCount());

        verify(organizationRepository).findAllOrganizationsWithOwnerAndMemberCount();
        verify(memberRepository).findAllById(List.of(ownerId));
        verify(mapper).map(organizationDetails, AdminOrganizationDTO.class);
        verify(mapper).map(ownerMember, MemberDTO.class);
    }
}
