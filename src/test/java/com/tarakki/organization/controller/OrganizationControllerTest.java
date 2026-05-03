package com.tarakki.organization.controller;

import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.service.OrganizationService;
import com.tarakki.organization.test_utils.factory.OrganizationTestDataFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrganizationService organizationService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrganizationDTO input;
    private OrganizationDTO output;

    @BeforeEach
    void setUp() {
        UUID owerId = UUID.randomUUID();
        input = OrganizationTestDataFactory.createOrganizationDTO(1L, owerId);
        output = OrganizationTestDataFactory.createOrganizationDTO(1L, owerId);
    }

    @Test
    void shouldCreateOrganization() throws Exception {

        when(organizationService.createOrganization(any()))
                .thenReturn(output);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orgName").value(output.getOrgName()))
                .andExpect(jsonPath("$.orgDesc").value(output.getOrgDesc()))
                .andExpect(jsonPath("$.orgAddress").value(output.getOrgAddress()))
                .andExpect(jsonPath("$.orgCity").value(output.getOrgCity()))
                .andExpect(jsonPath("$.orgState").value(output.getOrgState()))
                .andExpect(jsonPath("$.orgPostalCode").value(output.getOrgPostalCode()))
                .andExpect(jsonPath("$.orgCountry").value(output.getOrgCountry()));

    }

    @Test
    void shouldReturnBadRequestWhenOrganizationNameIsMissing() throws Exception {

        input.setOrgName(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOwnerIdIsMissing() throws Exception {

        input.setOwnerId(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrganizationDescIsMissing() throws Exception {

        input.setOrgDesc(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrganizationAddressIsMissing() throws Exception {

        input.setOrgAddress(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrganizationCityIsMissing() throws Exception {

        input.setOrgCity(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrganizationStateIsMissing() throws Exception {

        input.setOrgState(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrgPostalCodeIsMissing() throws Exception {

        input.setOrgPostalCode(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenOrgCountryIsMissing() throws Exception {

        input.setOrgCountry(null);

        mockMvc.perform(post("/api/organizations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest());
    }

}
