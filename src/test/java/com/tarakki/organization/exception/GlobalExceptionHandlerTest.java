package com.tarakki.organization.exception;

import com.tarakki.organization.exceptionhandling.GlobalExceptionHandler;
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
}
