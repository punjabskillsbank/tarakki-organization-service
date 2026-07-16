package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.dto.OrgMemberDto;
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

@Service
@RequiredArgsConstructor
public class OrgMemberServiceImpl implements OrgMemberService {

    private final ModelMapper modelMapper;
    private final OrgMemberRepository orgMemberRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public OrgMemberDto addOrgMemberInfo(OrgMemberDto orgMemberDto, Long orgId) {

        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new OrganizationNotFoundException(orgId));

        OrgMember orgMember = modelMapper.map(orgMemberDto, OrgMember.class);

        if (orgMember.getMemberAccountStatus() == null) {
            orgMember.setMemberAccountStatus(MemberAccountStatus.PENDING);
        }
        OrgMember savedOrgMember = orgMemberRepository.save(orgMember);

        return modelMapper.map(savedOrgMember, OrgMemberDto.class);
    }
}
