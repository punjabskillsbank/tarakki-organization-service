package com.tarakki.organization.controller;

import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDTO> createOrganization(@Valid @RequestBody OrganizationDTO organizationRequest) {
        OrganizationDTO savedOrganization = organizationService.createOrganization(organizationRequest);
        return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    }

    @GetMapping("/{organizationId}")
    public ResponseEntity<OrganizationDTO> getOrganizationById(
            @PathVariable Long organizationId,
            @RequestParam UUID memberId) {
        OrganizationDTO organizationDTO = organizationService.getOrganizationById(organizationId, memberId);
        return new ResponseEntity<>(organizationDTO, HttpStatus.OK);
    }

    @GetMapping("/{organizationId}/members/{memberId}/exists")
    public ResponseEntity<Boolean> existsMemberInOrganization(
            @PathVariable Long organizationId,
            @PathVariable UUID memberId) {
        boolean exists = organizationService.existsMemberInOrganization(organizationId, memberId);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }

    @PatchMapping("/{organizationId}")
    public ResponseEntity<OrganizationDTO> updateOrganization(
            @PathVariable Long organizationId,
            @Valid @RequestBody OrganizationDTO organizationRequest) {
        OrganizationDTO updatedOrganization =
                organizationService.updateOrganization(organizationId, organizationRequest);
        return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
    }
}
