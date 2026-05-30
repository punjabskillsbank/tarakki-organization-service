package com.tarakki.organization.controller;

import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.service.AdminOrganizationService;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminOrganizationService adminOrganizationService;

    private AdminOrganizationDTO output;

    @BeforeEach
    void setUp() {
        output = OrganizationTestDataFactory.createAdminOrganizationDTO(1L, UUID.randomUUID(), 3L);
    }

    @Test
    void shouldGetAllOrganizationsForAdmin() throws Exception {
        when(adminOrganizationService.getAllOrganizations()).thenReturn(List.of(output));

        mockMvc.perform(get("/api/admin/organizations/getAllOrganizations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orgId").value(output.getOrgId()))
                .andExpect(jsonPath("$[0].orgName").value(output.getOrgName()))
                .andExpect(jsonPath("$[0].orgDesc").value(output.getOrgDesc()))
                .andExpect(jsonPath("$[0].ownerId").value(output.getOwnerId().toString()))
                .andExpect(jsonPath("$[0].orgAddress").value(output.getOrgAddress()))
                .andExpect(jsonPath("$[0].orgCity").value(output.getOrgCity()))
                .andExpect(jsonPath("$[0].orgState").value(output.getOrgState()))
                .andExpect(jsonPath("$[0].orgPostalCode").value(output.getOrgPostalCode()))
                .andExpect(jsonPath("$[0].orgCountry").value(output.getOrgCountry()))
                .andExpect(jsonPath("$[0].totalMemberCount").value(output.getTotalMemberCount()));
    }
}
