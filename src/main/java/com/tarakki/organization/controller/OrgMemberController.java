package com.tarakki.organization.controller;

import com.tarakki.common.dto.OrgMemberDTO;
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
    public ResponseEntity<OrgMemberDTO> addOrgMemberInfo(@Valid @RequestBody OrgMemberDTO orgMemberDto,
                                                         @PathVariable Long orgId) {

        OrgMemberDTO response = orgMemberService.addOrgMemberInfo(orgMemberDto,orgId);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}

