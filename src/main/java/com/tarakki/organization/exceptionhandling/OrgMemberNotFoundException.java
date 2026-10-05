package com.tarakki.organization.exceptionhandling;

public class OrgMemberNotFoundException extends RuntimeException {
    public OrgMemberNotFoundException(Long orgMemberId, Long orgId) {
        super("Org member with ID " + orgMemberId + " not found in organization " + orgId);
    }
}
