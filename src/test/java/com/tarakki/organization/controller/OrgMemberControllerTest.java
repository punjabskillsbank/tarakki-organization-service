package com.tarakki.organization.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarakki.organization.exceptionhandling.OrganizationNotFoundException;
import com.tarakki.organization.dto.OrgMemberDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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

    private OrgMemberDto dto;
    private Long orgId;

    @BeforeEach
    void setUp() {
        orgId = OrganizationTestDataFactory.createOrganizationId();
        dto = OrgMemberTestDataFactory.createOrgMemberDTO();
    }

    @Test
    void shouldCreateOrgMember() throws Exception {
        when(orgMemberService.addOrgMemberInfo(any(OrgMemberDto.class), eq(orgId)))
                .thenReturn(dto);

        mockMvc.perform(post("/api/orgMember/{orgId}", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orgId").value(dto.getOrgId()))
                .andExpect(jsonPath("$.memberId").value(dto.getMemberId().toString()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.status").value(dto.getStatus().toString()))
                .andExpect(jsonPath("$.orgMemberRole").value(dto.getOrgMemberRole().toString()));
    }

    @Test
    void shouldReturnNotFoundExceptionWhenOrgIdIsNotFound() throws Exception {

        when(orgMemberService.addOrgMemberInfo(any(OrgMemberDto.class), eq(orgId)))
                .thenThrow(new OrganizationNotFoundException(orgId));

        mockMvc.perform(post("/api/orgMember/{orgId}", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(
                        "Organization with ID " + orgId + " not found"));
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsMissing() throws Exception {

        dto.setEmail(null);

        mockMvc.perform(post("/api/orgMember/{orgId}", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrgMemberRoleIsMissing() throws Exception {

        dto.setOrgMemberRole(null);

        mockMvc.perform(post("/api/orgMember/{orgId}", orgId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
