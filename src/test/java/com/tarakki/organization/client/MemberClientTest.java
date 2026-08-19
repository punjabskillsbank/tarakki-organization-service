package com.tarakki.organization.client;

import com.tarakki.common.dto.MemberDTO;
import com.tarakki.organization.test_utils.factory.AdminOrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
    private RestClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private RestClient.RequestBodySpec requestBodySpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    private MemberClient memberClient;


    @Value("${base-url}")
    private String baseUrl;

    @BeforeEach
    void setup() {
        memberClient = new MemberClient(restClient);
        ReflectionTestUtils.setField(memberClient, "memberApiEndpoint", "/api/members");
    }

    private void stubRestClientChain(UUID memberId) {
        doReturn(requestHeadersUriSpec).when(restClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(eq(baseUrl+"/api/members/{memberId}"), eq(memberId));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
    }

    private void stubRestClientChainForAllMembers() {
        doReturn(requestHeadersUriSpec).when(restClient).get();
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(eq(baseUrl+"/api/members"));
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();
    }

    private void stubRestClientChainForCreate() {
        doReturn(requestBodyUriSpec).when(restClient).post();
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(eq(baseUrl+"/api/members"));
        doReturn(requestBodySpec).when(requestBodySpec).contentType(MediaType.APPLICATION_JSON);
        doReturn(requestBodySpec).when(requestBodySpec).body(any(MemberDTO.class));
        doReturn(responseSpec).when(requestBodySpec).retrieve();
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

    @Test
    void findMemberByEmail_shouldReturnMatchingMember() {
        MemberDTO expectedMember = AdminOrganizationTestDataFactory.createMemberDTO(UUID.randomUUID());

        stubRestClientChainForAllMembers();
        when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenReturn(List.of(expectedMember));

        MemberDTO result = memberClient.findMemberByEmail(expectedMember.getEmail());

        assertNotNull(result);
        assertEquals(expectedMember.getMemberId(), result.getMemberId());
        assertEquals(expectedMember.getEmail(), result.getEmail());
    }

    @Test
    void findMemberByEmail_shouldReturnNullWhenEmailIsNotRegistered() {
        MemberDTO existingMember = AdminOrganizationTestDataFactory.createMemberDTO(UUID.randomUUID());

        stubRestClientChainForAllMembers();
        when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenReturn(List.of(existingMember));

        MemberDTO result = memberClient.findMemberByEmail("missing@tarakki.com");

        assertNull(result);
    }

    @Test
    void findMemberByEmail_shouldReturnNullWhenThereAreNoMembers() {
        stubRestClientChainForAllMembers();
        when(responseSpec.body(any(ParameterizedTypeReference.class)))
                .thenReturn(List.of());

        MemberDTO result = memberClient.findMemberByEmail("missing@tarakki.com");

        assertNull(result);
    }

    @Test
    void createMember_shouldReturnCreatedMemberDtoWhenSuccessful() {
        MemberDTO expectedMember = AdminOrganizationTestDataFactory.createMemberDTO(UUID.randomUUID());

        MemberDTO memberRequest = new MemberDTO();
        memberRequest.setEmail(expectedMember.getEmail());

        stubRestClientChainForCreate();
        when(responseSpec.body(MemberDTO.class)).thenReturn(expectedMember);

        MemberDTO result = memberClient.createMember(memberRequest);

        assertNotNull(result);
        assertEquals(expectedMember.getMemberId(), result.getMemberId());
        assertEquals(expectedMember.getEmail(), result.getEmail());
    }

    @Test
    void createMember_shouldPropagateRestClientExceptionWhenError() {
        MemberDTO memberRequest = new MemberDTO();
        memberRequest.setEmail("bob@tarakki.com");

        stubRestClientChainForCreate();
        when(responseSpec.body(MemberDTO.class)).thenThrow(new RestClientException("500 Internal Server Error"));

        assertThrows(RestClientException.class, () -> memberClient.createMember(memberRequest));
    }
}
