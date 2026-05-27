package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Organization;
import com.tarakki.common.enums.AccountStatus;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.dto.OrganizationOwnerDTO;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import com.tarakki.organization.repository.MemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.repository.projection.AdminOrganizationProjection;
import com.tarakki.organization.service.OrganizationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public OrganizationDTO createOrganization(OrganizationDTO organizationRequest) {
        memberRepository.findById(organizationRequest.getOwnerId())
                .orElseThrow(() -> new OwnerIdNotFoundException(organizationRequest.getOwnerId()));
        Organization organization = mapper.map(organizationRequest, Organization.class);
        Organization savedOrganization = organizationRepository.save(organization);
        return mapper.map(savedOrganization, OrganizationDTO.class);
    }

    @Override
    public List<AdminOrganizationDTO> getAllOrganizationsForAdmin() {
        return organizationRepository.findAllOrganizationsWithOwnerAndMemberCount().stream()
                .map(this::mapToAdminOrganizationDTO)
                .toList();
    }

    private AdminOrganizationDTO mapToAdminOrganizationDTO(AdminOrganizationProjection organization) {
        OrganizationOwnerDTO owner = OrganizationOwnerDTO.builder()
                .memberId(organization.getOwnerId())
                .firstName(organization.getOwnerFirstName())
                .lastName(organization.getOwnerLastName())
                .email(organization.getOwnerEmail())
                .profilePhotoS3Key(organization.getOwnerProfilePhotoS3Key())
                .accountStatus(AccountStatus.valueOf(organization.getOwnerAccountStatus()))
                .build();

        return AdminOrganizationDTO.builder()
                .orgId(organization.getOrgId())
                .orgName(organization.getOrgName())
                .orgDesc(organization.getOrgDesc())
                .owner(owner)
                .orgAddress(organization.getOrgAddress())
                .orgCity(organization.getOrgCity())
                .orgState(organization.getOrgState())
                .orgPostalCode(organization.getOrgPostalCode())
                .orgCountry(organization.getOrgCountry())
                .totalMemberCount(organization.getTotalMemberCount())
                .build();
    }
}
