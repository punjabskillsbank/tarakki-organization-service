package com.tarakki.organization.client;

import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MemberClient {

    private final RestClient restClient;

    public void validateMemberExists(UUID memberId) {
        try {
            restClient.get()
                    .uri("/api/members/{memberId}", memberId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException exception) {
            throw new OwnerIdNotFoundException(memberId);
        }
    }
}
