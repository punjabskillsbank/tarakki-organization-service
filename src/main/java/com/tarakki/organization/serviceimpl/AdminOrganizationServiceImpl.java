package com.tarakki.organization.serviceimpl;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.client.MemberClient;
import com.tarakki.organization.repository.OrganizationRepository;
import com.tarakki.organization.service.AdminOrganizationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrganizationServiceImpl implements AdminOrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ModelMapper mapper;
    private final MemberClient memberClient;

    @Override
    public List<AdminOrganizationDTO> getAllOrganizations() {
        List<Map<String, Object>> orgDetailsList = organizationRepository.findAllOrganizationsWithOwnerAndMemberCount();

        List<UUID> ownerIds = orgDetailsList.stream()
                .map(details -> (UUID) details.get("ownerId"))
                .filter(Objects::nonNull)
                .distinct()
                .toList();
        Map<UUID, MemberDTO> membersMap = ownerIds.stream()
                .map(memberClient::getMemberById)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(member -> member.getMemberId(), Function.identity()));

        return orgDetailsList.stream()
                .map(details -> {
                    AdminOrganizationDTO dto = mapper.map(details, AdminOrganizationDTO.class);
                    UUID ownerId = (UUID) details.get("ownerId");
                    if (ownerId != null) {
                        dto.setOwner(membersMap.get(ownerId));
                    }
                    return dto;
                })
                .toList();
    }
}
