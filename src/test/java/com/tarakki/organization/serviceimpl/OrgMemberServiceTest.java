package com.tarakki.organization.serviceimpl;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.common.entity.Organization;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.dto.OrgMemberRequestDTO;
import com.tarakki.organization.exceptionhandling.MemberEmailNotFoundException;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.entity.OrgMember;
import com.tarakki.organization.repository.OrgMemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.test_utils.factory.OrgMemberTestDataFactory;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

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

    @Mock
    private MemberClient memberClient;

    private OrgMember orgMember;
    private OrgMemberDTO orgMemberDto;
    private OrgMemberRequestDTO orgMemberRequestDto;
    private MemberDTO member;
    private Organization organization;
    private List<OrgMember> orgMembers;
    private List<OrgMemberDTO> orgMemberDtos;

    @BeforeEach
    void setup() {
        orgMember = OrgMemberTestDataFactory.createOrgMember();

        orgMemberDto = OrgMemberTestDataFactory.createOrgMemberDTO();

        orgMemberRequestDto = OrgMemberTestDataFactory.createOrgMemberRequestDTO();

        UUID memberId = orgMemberDto.getMemberId();

        member = OrganizationTestDataFactory.createMemberDTO(memberId, orgMemberRequestDto.getEmail());

        organization = OrganizationTestDataFactory.createOrganizationEntity(orgMemberDto.getOrgId(), memberId);

        orgMembers = OrgMemberTestDataFactory.createOrgMemberList();

        orgMemberDtos = OrgMemberTestDataFactory.createOrgMemberDTOList();
    }

    @Test
    void createOrgMember() {
        when(organizationRepository.findById(orgMemberDto.getOrgId()))
                .thenReturn(Optional.of(organization));

        when(memberClient.getMemberByEmail(orgMemberRequestDto.getEmail()))
                .thenReturn(member);

        when(mapper.map(any(OrgMemberRequestDTO.class), eq(OrgMember.class)))
                .thenReturn(orgMember);

        when(orgMemberRepository.save(any(OrgMember.class)))
                .thenReturn(orgMember);

        when(mapper.map(any(OrgMember.class), eq(OrgMemberDTO.class)))
                .thenReturn(orgMemberDto);

        OrgMemberDTO result = orgMemberService.addMemberToOrg(orgMemberRequestDto, orgMemberDto.getOrgId());

        assertNotNull(result);
        assertEquals(orgMemberDto.getOrgId(), result.getOrgId());
        assertEquals(orgMemberDto.getMemberId(), result.getMemberId());
        assertEquals(orgMemberDto.getMemberAccountStatus(), result.getMemberAccountStatus());
        assertEquals(orgMemberDto.getOrgMemberRole(), result.getOrgMemberRole());

        ArgumentCaptor<OrgMember> orgMemberCaptor = ArgumentCaptor.forClass(OrgMember.class);

        verify(organizationRepository).findById(orgMemberDto.getOrgId());
        verify(memberClient).getMemberByEmail(orgMemberRequestDto.getEmail());
        verify(mapper).map(any(OrgMemberRequestDTO.class), eq(OrgMember.class));
        verify(orgMemberRepository).save(orgMemberCaptor.capture());
        verify(mapper).map(any(OrgMember.class), eq(OrgMemberDTO.class));

        assertEquals(orgMemberDto.getOrgId(), orgMemberCaptor.getValue().getOrgId());
        assertEquals(member.getMemberId(), orgMemberCaptor.getValue().getMemberId());
    }

    @Test
    void createOrgMember_shouldThrowMemberEmailNotFoundExceptionWhenEmailHasNoMember() {
        when(organizationRepository.findById(orgMemberDto.getOrgId()))
                .thenReturn(Optional.of(organization));

        when(memberClient.getMemberByEmail(orgMemberRequestDto.getEmail()))
                .thenThrow(HttpClientErrorException.create(HttpStatus.NOT_FOUND,
                        "Not Found", null, null, null));

        MemberEmailNotFoundException exception = assertThrows(MemberEmailNotFoundException.class,
                () -> orgMemberService.addMemberToOrg(orgMemberRequestDto, orgMemberDto.getOrgId()));

        assertEquals("Member with email " + orgMemberRequestDto.getEmail() + " not found",
                exception.getMessage());

        verify(organizationRepository).findById(orgMemberDto.getOrgId());
        verify(memberClient).getMemberByEmail(orgMemberRequestDto.getEmail());
        verify(mapper, never()).map(any(), any());
        verify(orgMemberRepository, never()).save(any(OrgMember.class));
    }

    @Test
    void createOrgMember_shouldThrowOrgNotFoundExceptionWhenOrgIdIsMissing() {
        when(organizationRepository.findById(orgMember.getOrgId()))
                .thenReturn(Optional.empty());

        OrganizationNotFoundException exception = assertThrows(OrganizationNotFoundException.class,
                () -> orgMemberService.addMemberToOrg(orgMemberRequestDto, orgMember.getOrgId()));

        assertEquals("Organization with ID " + orgMember.getOrgId() + " not found", exception.getMessage());

        verify(organizationRepository).findById(orgMember.getOrgId());
        verify(memberClient, never()).getMemberByEmail(any());
        verify(orgMemberRepository, never()).save(any(OrgMember.class));
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
        assertEquals(orgMemberDtos.get(0).getMemberAccountStatus(), result.get(0).getMemberAccountStatus());
        assertEquals(orgMemberDtos.get(0).getOrgMemberRole(), result.get(0).getOrgMemberRole());
        assertEquals(orgMemberDtos.get(1).getMemberId(), result.get(1).getMemberId());
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
