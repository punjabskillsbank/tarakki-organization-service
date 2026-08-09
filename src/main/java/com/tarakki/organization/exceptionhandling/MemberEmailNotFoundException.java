package com.tarakki.organization.exceptionhandling;

public class MemberEmailNotFoundException extends RuntimeException {
    public MemberEmailNotFoundException(String email) {
        super("Member with email " + email + " not found");
    }
}
