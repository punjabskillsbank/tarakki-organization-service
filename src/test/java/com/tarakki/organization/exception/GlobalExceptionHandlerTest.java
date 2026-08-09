package com.tarakki.organization.exception;

import com.tarakki.organization.exceptionhandling.GlobalExceptionHandler;
import com.tarakki.organization.exceptionhandling.MemberEmailNotFoundException;
import com.tarakki.organization.exceptionhandling.OwnerIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    @Test
    void handleOwnerIdNotFound_shouldReturnNotFoundStatus() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        UUID ownerId = UUID.randomUUID();
        OwnerIdNotFoundException exception = new OwnerIdNotFoundException(ownerId);

        ResponseEntity<String> response = exceptionHandler.handleOwnerIdNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getBody());
    }

    @Test
    void handleMemberEmailNotFound_shouldReturnNotFoundStatus() {
        GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();
        String email = "missing@tarakki.com";
        MemberEmailNotFoundException exception = new MemberEmailNotFoundException(email);

        ResponseEntity<String> response = exceptionHandler.handleMemberEmailNotFound(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Member with email " + email + " not found", response.getBody());
    }
}
