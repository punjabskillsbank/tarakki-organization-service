package com.tarakki.organization.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberClientTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec<?> requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec<?> requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private MemberClient memberClient;

    @BeforeEach
    void setup() {
        memberClient = new MemberClient(restClient, "/api/members");
    }

    private void stubRestClientChain(UUID memberId) {
        doReturn(requestHeadersUriSpec).when(restClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(eq("/api/members/{memberId}"), eq(memberId));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
    }

    @Test
    void doesMemberExist_shouldReturnTrueWhenMemberExists() {
        UUID memberId = UUID.randomUUID();

        stubRestClientChain(memberId);
        when(responseSpec.toBodilessEntity()).thenReturn(ResponseEntity.ok().build());

        assertTrue(memberClient.doesMemberExist(memberId));
    }

    @Test
    void doesMemberExist_shouldReturnFalseWhenMemberNotFound() {
        UUID memberId = UUID.randomUUID();

        stubRestClientChain(memberId);
        when(responseSpec.toBodilessEntity()).thenThrow(new RestClientException("404 Not Found"));

        assertFalse(memberClient.doesMemberExist(memberId));
    }
}
