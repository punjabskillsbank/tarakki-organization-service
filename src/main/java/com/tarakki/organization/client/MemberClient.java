package com.tarakki.organization.client;

import com.tarakki.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberClient {

    private final RestClient restClient;

    @Value("${member.api.endpoint}")
    private String memberApiEndpoint;

    @Value("${base-url}")
    private String baseUrl;

    public MemberDTO getMemberById(UUID memberId) {
        return restClient.get()
                .uri(baseUrl + memberApiEndpoint + "/{memberId}", memberId)
                .retrieve()
                .body(MemberDTO.class);
    }

    public MemberDTO findMemberByEmail(String email) {

        List<MemberDTO> members = restClient.get()
                .uri(baseUrl + memberApiEndpoint)
                .retrieve()
                .body(new ParameterizedTypeReference<List<MemberDTO>>() {
                });

        return members.stream()
                .filter(member -> Objects.equals(member.getEmail(), email))
                .findFirst()
                .orElse(null);
    }

    public MemberDTO createMember(MemberDTO memberRequest) {
        return restClient.post()
                .uri(baseUrl + memberApiEndpoint)
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberRequest)
                .retrieve()
                .body(MemberDTO.class);
    }
}
