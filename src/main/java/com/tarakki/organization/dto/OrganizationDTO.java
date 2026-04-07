package com.tarakki.organization.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
    @NotBlank(message = "Organization name must not be empty")
    private String orgName;
    @NotBlank(message = "Description must not be empty")
    private String orgDesc;
    @NotBlank(message = "Owner Id must not be empty")
    private UUID ownerId;
    @NotBlank(message = "Address must not be empty")
    private String orgAddress;
    @NotBlank(message = "City must not be empty")
    private String orgCity;
    @NotBlank(message = "State must not be empty")
    private String orgState;
    @NotBlank(message = "Postal Code must not be empty")
    private String orgPostalCode;
    @NotBlank(message = "Country must not be empty")
    private String orgCountry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
