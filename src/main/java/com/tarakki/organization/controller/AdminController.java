package com.tarakki.organization.controller;

import com.tarakki.organization.dto.AdminOrganizationDTO;
import com.tarakki.organization.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/organizations")
@RequiredArgsConstructor
public class AdminController {

    private final OrganizationService organizationService;

    @GetMapping({"", "/getAllOrganizations"})
    public ResponseEntity<List<AdminOrganizationDTO>> getAllOrganizations() {
        List<AdminOrganizationDTO> organizations = organizationService.getAllOrganizationsForAdmin();
        return new ResponseEntity<>(organizations, HttpStatus.OK);
    }
}
