package com.tarakki.organization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.exceptionhandling.GlobalExceptionHandler;
import com.tarakki.organization.service.OrgMemberService;
import com.tarakki.organization.test_utils.factory.OrgMemberTestDataFactory;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrgMemberController.class)
@Import(GlobalExceptionHandler.class)
public class OrgMemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrgMemberService orgMemberService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private OrgMemberDTO dto;
    private Long orgId;
    private List<OrgMemberDTO> orgMemberDtos;

    @BeforeEach
    void setUp() {
        orgId = OrganizationTestDataFactory.createOrganizationId();
        dto = OrgMemberTestDataFactory.createOrgMemberDTO();
        orgMemberDtos = OrgMemberTestDataFactory.createOrgMemberDTOList();
    }

    @Test
    void shouldCreateOrgMember() throws Exception {
        when(orgMemberService.addMemberToOrg(any(OrgMemberDTO.class), eq(orgId)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/organizations/{orgId}/members", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orgId").value(dto.getOrgId()))
                .andExpect(jsonPath("$.memberId").value(dto.getMemberId().toString()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.memberAccountStatus").value(dto.getMemberAccountStatus().toString()))
                .andExpect(jsonPath("$.orgMemberRole").value(dto.getOrgMemberRole().toString()));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenOrgIdIsNotFound() throws Exception {

        when(orgMemberService.addMemberToOrg(any(OrgMemberDTO.class), eq(orgId)))
                .thenThrow(new OrganizationNotFoundException(orgId));

        mockMvc.perform(post("/api/organizations/{orgId}/members", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(
                        "Organization with ID " + orgId + " not found"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsMissing() throws Exception {

        dto.setEmail(null);

        mockMvc.perform(post("/api/organizations/{orgId}/members", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrgIdIsMissing() throws Exception {

        dto.setOrgId(null);

        mockMvc.perform(post("/api/organizations/{orgId}/members", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMemberAccountStatusIsMissing() throws Exception {

        dto.setMemberAccountStatus(null);

        mockMvc.perform(post("/api/organizations/{orgId}/members", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMemberAccountStatusIsAbsentFromBody() throws Exception {

        ObjectNode body = objectMapper.valueToTree(dto);
        body.remove("memberAccountStatus");

        mockMvc.perform(post("/api/organizations/{orgId}/members", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.memberAccountStatus")
                        .value("MemberAccountStatus must not be empty"));
    }

    @Test
    void shouldReturnBadRequestWhenOrgMemberRoleIsMissing() throws Exception {

        dto.setOrgMemberRole(null);

        mockMvc.perform(post("/api/organizations/{orgId}/members", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnMembersByOrgId() throws Exception {

        when(orgMemberService.getMembersByOrgId(orgId))
                .thenReturn(orgMemberDtos);

        mockMvc.perform(get("/api/organizations/{orgId}/members", orgId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(orgMemberDtos.size()))
                .andExpect(jsonPath("$[0].orgMemberId").value(orgMemberDtos.get(0).getOrgMemberId()))
                .andExpect(jsonPath("$[0].orgId").value(orgMemberDtos.get(0).getOrgId()))
                .andExpect(jsonPath("$[0].memberId").value(orgMemberDtos.get(0).getMemberId().toString()))
                .andExpect(jsonPath("$[0].memberAccountStatus")
                        .value(orgMemberDtos.get(0).getMemberAccountStatus().toString()))
                .andExpect(jsonPath("$[0].orgMemberRole")
                        .value(orgMemberDtos.get(0).getOrgMemberRole().toString()))
                .andExpect(jsonPath("$[1].memberId").value(orgMemberDtos.get(1).getMemberId().toString()))
                .andExpect(jsonPath("$[1].orgMemberRole")
                        .value(orgMemberDtos.get(1).getOrgMemberRole().toString()));
    }

    @Test
    void shouldReturnEmptyListWhenOrgHasNoMembers() throws Exception {

        when(orgMemberService.getMembersByOrgId(orgId))
                .thenReturn(List.of());

        mockMvc.perform(get("/api/organizations/{orgId}/members", orgId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenOrgIdIsNotFoundOnGetMembers() throws Exception {

        when(orgMemberService.getMembersByOrgId(orgId))
                .thenThrow(new OrganizationNotFoundException(orgId));

        mockMvc.perform(get("/api/organizations/{orgId}/members", orgId))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(
                        "Organization with ID " + orgId + " not found"));
    }
}
