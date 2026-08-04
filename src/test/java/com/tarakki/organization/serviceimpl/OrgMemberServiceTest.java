package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.entity.OrgMember;
import com.tarakki.organization.repository.OrgMemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.test_utils.factory.OrgMemberTestDataFactory;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrgMemberServiceTest {

    @Mock
    private ModelMapper mapper;

    @Mock
    private OrgMemberRepository orgMemberRepository;

    @InjectMocks
    private OrgMemberServiceImpl orgMemberService;

    @Mock
    private OrganizationRepository organizationRepository;

    private OrgMember orgMember;
    private OrgMemberDTO orgMemberDto;
    private Organization organization;
    private List<OrgMember> orgMembers;
    private List<OrgMemberDTO> orgMemberDtos;

    @BeforeEach
    void setup() {
        orgMember = OrgMemberTestDataFactory.createOrgMember();

        orgMemberDto = OrgMemberTestDataFactory.createOrgMemberDTO();

        UUID memberId = orgMemberDto.getMemberId();

        organization = OrganizationTestDataFactory.createOrganizationEntity(orgMemberDto.getOrgId(), memberId);

        orgMembers = OrgMemberTestDataFactory.createOrgMemberList();

        orgMemberDtos = OrgMemberTestDataFactory.createOrgMemberDTOList();
    }

    @Test
    void createOrgMember() {
        when(organizationRepository.findById(orgMemberDto.getOrgId()))
                .thenReturn(Optional.of(organization));

        when(mapper.map(any(OrgMemberDTO.class), eq(OrgMember.class)))
                .thenReturn(orgMember);

        when(orgMemberRepository.save(any(OrgMember.class)))
                .thenReturn(orgMember);

        when(mapper.map(any(OrgMember.class), eq(OrgMemberDTO.class)))
                .thenReturn(orgMemberDto);

        OrgMemberDTO result = orgMemberService.addMemberToOrg(orgMemberDto, orgMemberDto.getOrgId());

        assertNotNull(result);
        assertEquals(orgMemberDto.getOrgId(), result.getOrgId());
        assertEquals(orgMemberDto.getMemberId(), result.getMemberId());
        assertEquals(orgMemberDto.getEmail(), result.getEmail());
        assertEquals(orgMemberDto.getMemberAccountStatus(), result.getMemberAccountStatus());
        assertEquals(orgMemberDto.getOrgMemberRole(), result.getOrgMemberRole());

        verify(organizationRepository).findById(orgMemberDto.getOrgId());
        verify(mapper).map(any(OrgMemberDTO.class), eq(OrgMember.class));
        verify(orgMemberRepository).save(any(OrgMember.class));
        verify(mapper).map(any(OrgMember.class), eq(OrgMemberDTO.class));
    }

    @Test
    void createOrgMember_shouldThrowOrgNotFoundExceptionWhenOrgIdIsMissing() {
        when(organizationRepository.findById(orgMember.getOrgId()))
                .thenReturn(Optional.empty());

        OrganizationNotFoundException exception = assertThrows(OrganizationNotFoundException.class,
                () -> orgMemberService.addMemberToOrg(orgMemberDto, orgMember.getOrgId()));

        assertEquals("Organization with ID " + orgMember.getOrgId() + " not found", exception.getMessage());

        verify(organizationRepository).findById(orgMember.getOrgId());
    }

    @Test
    void getMembersByOrgId_shouldReturnOrgMemberDTOList() {
        Long orgId = organization.getOrgId();

        when(organizationRepository.findById(orgId))
                .thenReturn(Optional.of(organization));

        when(orgMemberRepository.findByOrgId(orgId))
                .thenReturn(orgMembers);

        when(mapper.map(orgMembers.get(0), OrgMemberDTO.class))
                .thenReturn(orgMemberDtos.get(0));

        when(mapper.map(orgMembers.get(1), OrgMemberDTO.class))
                .thenReturn(orgMemberDtos.get(1));

        List<OrgMemberDTO> result = orgMemberService.getMembersByOrgId(orgId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(orgMemberDtos.get(0).getOrgMemberId(), result.get(0).getOrgMemberId());
        assertEquals(orgMemberDtos.get(0).getOrgId(), result.get(0).getOrgId());
        assertEquals(orgMemberDtos.get(0).getMemberId(), result.get(0).getMemberId());
        assertEquals(orgMemberDtos.get(0).getEmail(), result.get(0).getEmail());
        assertEquals(orgMemberDtos.get(0).getMemberAccountStatus(), result.get(0).getMemberAccountStatus());
        assertEquals(orgMemberDtos.get(0).getOrgMemberRole(), result.get(0).getOrgMemberRole());
        assertEquals(orgMemberDtos.get(1).getEmail(), result.get(1).getEmail());
        assertEquals(orgMemberDtos.get(1).getOrgMemberRole(), result.get(1).getOrgMemberRole());

        verify(organizationRepository).findById(orgId);
        verify(orgMemberRepository).findByOrgId(orgId);
        verify(mapper).map(orgMembers.get(0), OrgMemberDTO.class);
        verify(mapper).map(orgMembers.get(1), OrgMemberDTO.class);
    }

    @Test
    void getMembersByOrgId_shouldThrowOrganizationNotFoundExceptionWhenOrgIdIsNotFound() {
        Long orgId = organization.getOrgId();

        when(organizationRepository.findById(orgId))
                .thenReturn(Optional.empty());

        OrganizationNotFoundException exception = assertThrows(OrganizationNotFoundException.class,
                () -> orgMemberService.getMembersByOrgId(orgId));

        assertEquals("Organization with ID " + orgId + " not found", exception.getMessage());

        verify(organizationRepository).findById(orgId);
        verify(orgMemberRepository, never()).findByOrgId(orgId);
        verify(mapper, never()).map(any(), any());
    }

    @Test
    void getMembersByOrgId_shouldReturnEmptyListWhenOrgHasNoMembers() {
        Long orgId = organization.getOrgId();

        when(organizationRepository.findById(orgId))
                .thenReturn(Optional.of(organization));

        when(orgMemberRepository.findByOrgId(orgId))
                .thenReturn(List.of());

        List<OrgMemberDTO> result = orgMemberService.getMembersByOrgId(orgId);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(organizationRepository).findById(orgId);
        verify(orgMemberRepository).findByOrgId(orgId);
        verify(mapper, never()).map(any(), any());
    }
}
