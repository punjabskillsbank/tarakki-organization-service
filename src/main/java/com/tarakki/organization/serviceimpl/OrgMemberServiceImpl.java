package com.tarakki.organization.serviceimpl;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.entity.OrgMember;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.repository.OrgMemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.OrgMemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public OrgMemberDTO addMemberToOrg(OrgMemberDTO orgMemberDto, Long orgId) {

        organizationRepository.findById(orgId)
                .orElseThrow(() -> new OrganizationNotFoundException(orgId));

        MemberDTO member = memberClient.findMemberByEmail(orgMemberDto.getEmail());

        if (member == null) {
            MemberDTO newMember = new MemberDTO();
            newMember.setEmail(orgMemberDto.getEmail());
            member = memberClient.createMember(newMember);
        }

        orgMemberDto.setMemberId(member.getMemberId());

        OrgMember orgMember = modelMapper.map(orgMemberDto, OrgMember.class);

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
