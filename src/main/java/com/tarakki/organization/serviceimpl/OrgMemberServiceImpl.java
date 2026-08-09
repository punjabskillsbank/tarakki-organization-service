package com.tarakki.organization.serviceimpl;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.dto.OrgMemberRequestDTO;
import com.tarakki.organization.entity.OrgMember;
import com.tarakki.organization.enums.MemberAccountStatus;
import com.tarakki.organization.exceptionhandling.MemberEmailNotFoundException;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.repository.OrgMemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.OrgMemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrgMemberServiceImpl implements OrgMemberService {

    private final ModelMapper modelMapper;
    private final OrgMemberRepository orgMemberRepository;
    private final OrganizationRepository organizationRepository;
    private final MemberClient memberClient;

    @Override
    @Transactional
    public OrgMemberDTO addMemberToOrg(OrgMemberRequestDTO orgMemberRequestDto, Long orgId) {

        organizationRepository.findById(orgId)
                .orElseThrow(() -> new OrganizationNotFoundException(orgId));

        MemberDTO member;
        try {
            member = memberClient.getMemberByEmail(orgMemberRequestDto.getEmail());
        } catch (HttpClientErrorException.NotFound exception) {
            throw new MemberEmailNotFoundException(orgMemberRequestDto.getEmail());
        }

        OrgMember orgMember = modelMapper.map(orgMemberRequestDto, OrgMember.class);
        orgMember.setOrgId(orgId);
        orgMember.setMemberId(member.getMemberId());

        if (orgMember.getMemberAccountStatus() == null) {
            orgMember.setMemberAccountStatus(MemberAccountStatus.PENDING);
        }
        OrgMember savedOrgMember = orgMemberRepository.save(orgMember);

        return modelMapper.map(savedOrgMember, OrgMemberDTO.class);
    }

    @Override
    public List<OrgMemberDTO> getMembersByOrgId(Long orgId) {
        organizationRepository.findById(orgId)
                .orElseThrow(() -> new OrganizationNotFoundException(orgId));

        List<OrgMember> orgMembers = orgMemberRepository.findByOrgId(orgId);

        return orgMembers.stream()
                .map(orgMember -> modelMapper.map(orgMember, OrgMemberDTO.class))
                .toList();
    }
}
