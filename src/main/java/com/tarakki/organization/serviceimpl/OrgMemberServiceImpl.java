package com.tarakki.organization.serviceimpl;

import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.entity.OrgMember;
import com.tarakki.organization.enums.MemberAccountStatus;
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

    @Override
    @Transactional
    public OrgMemberDTO addMemberToOrg(OrgMemberDTO orgMemberDto, Long orgId) {

        if (!organizationRepository.existsById(orgId)) {
            throw new OrganizationNotFoundException(orgId);
        }

        OrgMember orgMember = modelMapper.map(orgMemberDto, OrgMember.class);

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
