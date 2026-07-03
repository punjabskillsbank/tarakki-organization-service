package com.tarakki.organization.client;

import com.tarakki.common.dto.MemberDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
public class MemberClient {

    private final RestClient restClient;
    private final String memberApiEndpoint;

    public MemberClient(RestClient restClient, @Value("${member.api.endpoint}") String memberApiEndpoint) {
        this.restClient = restClient;
        this.memberApiEndpoint = memberApiEndpoint;
    }

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
        try {
            return restClient.get()
                    .uri(memberApiEndpoint + "/{memberId}", memberId)
                    .retrieve()
                    .body(MemberDTO.class);
        } catch (RestClientException exception) {
            return null;
        }
    }
}
