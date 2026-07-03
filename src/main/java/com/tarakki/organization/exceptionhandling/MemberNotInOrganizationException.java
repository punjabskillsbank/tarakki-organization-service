package com.tarakki.organization.exceptionhandling;

import java.util.UUID;

public class MemberNotInOrganizationException extends RuntimeException {
    public MemberNotInOrganizationException(UUID memberId, Long orgId) {
        super("Member " + memberId + " does not belong to organization " + orgId);
    }
}
