package com.tarakki.organization.controller;

import com.tarakki.organization.dto.OrgMemberDto;
import com.tarakki.organization.service.OrgMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orgMember")
@RequiredArgsConstructor
public class OrgMemberController {

    private final OrgMemberService orgMemberService;

    @PostMapping("/{orgId}")
    public ResponseEntity<OrgMemberDto> addOrgMemberInfo(@Valid @RequestBody OrgMemberDto orgMemberDto,
                                                            @PathVariable Long orgId) {

        OrgMemberDto response = orgMemberService.addOrgMemberInfo(orgMemberDto,orgId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}

