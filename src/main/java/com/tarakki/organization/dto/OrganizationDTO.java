package com.tarakki.organization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
    private Long orgId;
    @NotBlank(message = "Organization name must not be empty")
    @Size(max = 255, message = "Organization name must not exceed 255 characters")
    private String orgName;
    @NotBlank(message = "Description must not be empty")
    private String orgDesc;
    @NotNull(message = "Owner Id must not be empty")
    private UUID ownerId;
    @NotBlank(message = "Address must not be empty")
    private String orgAddress;
    @NotBlank(message = "City must not be empty")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String orgCity;
    @NotBlank(message = "State must not be empty")
    @Size(max = 100, message = "State must not exceed 100 characters")
    private String orgState;
    @NotBlank(message = "Postal Code must not be empty")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Please enter a valid postal code")
    private String orgPostalCode;
    @NotBlank(message = "Country must not be empty")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String orgCountry;
}
