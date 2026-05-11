package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Member;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.common.entity.Organization;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.MemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.OrganizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        Optional<Member> owner = Optional.of(memberRepository.findById(organizationRequest.getOwnerId())
                .orElseThrow(() -> new OwnerIdNotFoundException(organizationRequest.getOwnerId())));
        Organization organization = mapper.map(organizationRequest, Organization.class);
        Organization savedOrganization = organizationRepository.save(organization);
        return mapper.map(savedOrganization, OrganizationDTO.class);
    }
}
