package com.tarakki.organization.client;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.organization.test_utils.factory.AdminOrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        ReflectionTestUtils.setField(memberClient, "memberApiEndpoint", "/api/members");
    }

    private void stubRestClientChain(UUID memberId) {
        doReturn(requestHeadersUriSpec).when(restClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(eq("/api/members/{memberId}"), eq(memberId));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
    }


    @Test
    void getMemberById_shouldReturnMemberDtoWhenSuccessful() {
        UUID memberId = UUID.randomUUID();
        MemberDTO expectedMember = AdminOrganizationTestDataFactory.createMemberDTO(memberId);

        stubRestClientChain(memberId);
        when(responseSpec.body(MemberDTO.class)).thenReturn(expectedMember);

        MemberDTO result = memberClient.getMemberById(memberId);

        assertNotNull(result);
        assertEquals(expectedMember, result);
    }

    @Test
    void getMemberById_shouldPropagateRestClientExceptionWhenError() {
        UUID memberId = UUID.randomUUID();

        stubRestClientChain(memberId);
        when(responseSpec.body(MemberDTO.class)).thenThrow(new RestClientException("500 Internal Server Error"));

        assertThrows(RestClientException.class, () -> memberClient.getMemberById(memberId));
    }
}

