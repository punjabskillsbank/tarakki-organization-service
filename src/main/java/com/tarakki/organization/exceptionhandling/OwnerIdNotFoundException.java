package com.tarakki.organization.exceptionhandling;

import java.util.UUID;

public class OwnerIdNotFoundException extends RuntimeException {
    public OwnerIdNotFoundException(UUID owner) {
        super("owner not found at given ownerId: " + owner);
    }
}
