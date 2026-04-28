package com.tarakki.organization.controller;

import com.tarakki.organization.dto.OrganizationDTO;
import com.tarakki.organization.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;

    @PostMapping("/create_organization")
    public ResponseEntity<OrganizationDTO> createOrganization(@Valid @RequestBody OrganizationDTO organizationRequest) {
        OrganizationDTO savedOrganization = organizationService.createOrganization(organizationRequest);
        return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    }
}
