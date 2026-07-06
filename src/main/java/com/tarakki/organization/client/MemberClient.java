package com.tarakki.organization.client;

import com.tarakki.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberClient {

    private final RestClient restClient;

    @Value("${member.api.endpoint}")
    private String memberApiEndpoint;

    public boolean doesMemberExist(UUID memberId) {
        try {
            restClient.get()
                    .uri(memberApiEndpoint + "/{memberId}", memberId)
                    .retrieve()
                    .toBodilessEntity();
            return true;
        } catch (RestClientException e) {
            return false;
        }
    }

    public MemberDTO getMemberById(UUID memberId) {
        return restClient.get()
                .uri(memberApiEndpoint + "/{memberId}", memberId)
                .retrieve()
                .body(MemberDTO.class);
    }
}

