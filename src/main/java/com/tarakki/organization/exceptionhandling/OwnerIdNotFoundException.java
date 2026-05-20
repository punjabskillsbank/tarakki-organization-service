package com.tarakki.organization.exceptionhandling;

import java.util.UUID;

public class OwnerIdNotFoundException extends RuntimeException {
    public OwnerIdNotFoundException(UUID ownerId) {
        super("owner not found at given ownerId: " + ownerId);
    }
}
