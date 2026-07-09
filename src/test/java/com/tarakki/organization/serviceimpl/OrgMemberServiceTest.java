package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.dto.OrgMemberDto;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private OrgMemberDto orgMemberDto;
    private Organization organization;

    @BeforeEach
    void setup() {
        orgMember = OrgMemberTestDataFactory.createOrgMember();

        orgMemberDto = OrgMemberTestDataFactory.createOrgMemberDTO();

        UUID memberId = orgMemberDto.getMemberId();

        organization = OrganizationTestDataFactory.createOrganizationEntity(orgMemberDto.getOrgId(), memberId);
    }

    @Test
    void createOrgMember() {
        when(organizationRepository.findById(orgMemberDto.getOrgId()))
                .thenReturn(Optional.of(organization));

        when(mapper.map(any(OrgMemberDto.class), eq(OrgMember.class)))
                .thenReturn(orgMember);

        when(orgMemberRepository.save(any(OrgMember.class)))
                .thenReturn(orgMember);

        when(mapper.map(any(OrgMember.class), eq(OrgMemberDto.class)))
                .thenReturn(orgMemberDto);

        OrgMemberDto result = orgMemberService.addOrgMemberInfo(orgMemberDto, orgMemberDto.getOrgId());

        assertNotNull(result);
        assertEquals(orgMemberDto.getOrgId(), result.getOrgId());
        assertEquals(orgMemberDto.getMemberId(), result.getMemberId());
        assertEquals(orgMemberDto.getEmail(), result.getEmail());
        assertEquals(orgMemberDto.getStatus(), result.getStatus());
        assertEquals(orgMemberDto.getOrgMemberRole(), result.getOrgMemberRole());

        verify(organizationRepository).findById(orgMemberDto.getOrgId());
        verify(mapper).map(any(OrgMemberDto.class), eq(OrgMember.class));
        verify(orgMemberRepository).save(any(OrgMember.class));
        verify(mapper).map(any(OrgMember.class), eq(OrgMemberDto.class));
    }

    @Test
    void createOrgMember_shouldThrowOrgNotFoundExceptionWhenOrgIdIsMissing() {
        when(organizationRepository.findById(orgMember.getOrgId()))
                .thenReturn(Optional.empty());

        OrganizationNotFoundException exception = assertThrows(OrganizationNotFoundException.class,
                () -> orgMemberService.addOrgMemberInfo(orgMemberDto, orgMember.getOrgId()));

        assertEquals("Organization with id " + orgMember.getOrgId() + " not found", exception.getMessage());

        verify(organizationRepository).findById(orgMember.getOrgId());
    }
}
