package com.tarakki.organization.serviceimpl;

import com.tarakki.common.entity.Member;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.repository.MemberRepository;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.AdminOrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrganizationServiceImpl implements AdminOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper mapper;

    @Override
    public List<AdminOrganizationDTO> getAllOrganizations() {
        List<Map<String, Object>> orgDetailsList = organizationRepository.findAllOrganizationsWithOwnerAndMemberCount();

        List<UUID> ownerIds = orgDetailsList.stream()
                .map(details -> (UUID) details.get("ownerId"))
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        Map<UUID, Member> membersMap = memberRepository.findAllById(ownerIds).stream()
                .collect(Collectors.toMap(Member::getMemberId, member -> member));

        return orgDetailsList.stream()
                .map(details -> {
                    AdminOrganizationDTO dto = mapper.map(details, AdminOrganizationDTO.class);
                    UUID ownerId = (UUID) details.get("ownerId");
                    if (ownerId != null && membersMap.containsKey(ownerId)) {
                        Member member = membersMap.get(ownerId);
                        dto.setOwner(member);
                    }
                    return dto;
                })
                .toList();
    }
}
