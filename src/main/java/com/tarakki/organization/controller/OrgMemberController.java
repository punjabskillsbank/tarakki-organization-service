package com.tarakki.organization.controller;


import com.tarakki.organization.dto.OrgMemberDTO;
import com.tarakki.organization.service.OrgMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations/{orgId}/members")
@RequiredArgsConstructor
public class OrgMemberController {

    private final OrgMemberService orgMemberService;

    @PostMapping
    public ResponseEntity<OrgMemberDTO> addMemberToOrg(@Valid @RequestBody OrgMemberDTO orgMemberDto,
                                                       @PathVariable Long orgId) {

        OrgMemberDTO response = orgMemberService.addMemberToOrg(orgMemberDto, orgId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<List<OrgMemberDTO>> getMembersByOrgId(@PathVariable Long orgId) {

        List<OrgMemberDTO> response = orgMemberService.getMembersByOrgId(orgId);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}

