package com.tarakki.organization.exceptionhandling;

public class InvalidOrganizationException extends RuntimeException {
    public InvalidOrganizationException(String message) {
        super(message);
    }
}
