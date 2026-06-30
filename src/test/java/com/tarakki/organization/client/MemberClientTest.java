package com.tarakki.organization.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;
import com.tarakki.organization.test_utils.factory.MemberTestDataFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        memberClient = new MemberClient(restClient);
    }

    private void stubRestClientChain(UUID memberId) {
        doReturn(requestHeadersUriSpec).when(restClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(eq("/api/members/{memberId}"), eq(memberId));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
    }

    @Test
    void getMemberById_shouldReturnResponseBodyWhenMemberExists() {
        UUID memberId = UUID.randomUUID();
        String expectedBody = MemberTestDataFactory.createMemberResponseBody(memberId);

        stubRestClientChain(memberId);
        when(responseSpec.toEntity(String.class)).thenReturn(ResponseEntity.ok(expectedBody));

        ResponseEntity<String> result = memberClient.getMemberById(memberId);

        assertEquals(expectedBody, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getMemberById_shouldPropagateRestClientExceptionWhenMemberNotFound() {
        UUID memberId = UUID.randomUUID();

        stubRestClientChain(memberId);
        when(responseSpec.toEntity(String.class))
                .thenThrow(new RestClientException("404 Not Found"));

        assertThrows(RestClientException.class,
                () -> memberClient.getMemberById(memberId));
    }
}
