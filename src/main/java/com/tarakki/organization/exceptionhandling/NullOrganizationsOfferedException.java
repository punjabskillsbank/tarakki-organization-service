package com.tarakki.organization.exceptionhandling;

public class NullOrganizationsOfferedException extends RuntimeException {
    public NullOrganizationsOfferedException() {
        super("Organizations cannot be null.");
    }
}
