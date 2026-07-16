package com.tarakki.organization.client;

import com.tarakki.common.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
        System.out.println(memberApiEndpoint + "/" + memberId);
        return restClient.get()
                .uri(baseUrl+memberApiEndpoint + "/{memberId}", memberId)
                .retrieve()
                .body(MemberDTO.class);
    }
}

