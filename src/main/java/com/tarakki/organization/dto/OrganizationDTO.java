package com.tarakki.organization.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDTO {
    @NotBlank(message = "Organization name must not be empty")
    private String orgName;
    @NotBlank(message = "Description must not be empty")
    private String orgDesc;
    @NotNull(message = "Owner Id must not be empty")
    private UUID ownerId;
    @NotBlank(message = "Address must not be empty")
    private String orgAddress;
    @NotBlank(message = "City must not be empty")
    private String orgCity;
    @NotBlank(message = "State must not be empty")
    private String orgState;
    @NotBlank(message = "Postal Code must not be empty")
    @Pattern(regexp = "^[1-9][0-9]{5}$", message = "Please enter a valid postal code")
    private String orgPostalCode;
    @NotBlank(message = "Country must not be empty")
    private String orgCountry;
}
