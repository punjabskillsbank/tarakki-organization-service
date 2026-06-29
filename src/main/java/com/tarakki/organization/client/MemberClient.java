package com.tarakki.organization.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberClient {

    private final RestClient restClient;

    public String getMemberById(UUID memberId) {
        return restClient.get()
                .uri("/api/members/{memberId}", memberId)
                .retrieve()
                .body(String.class);
    }
}
