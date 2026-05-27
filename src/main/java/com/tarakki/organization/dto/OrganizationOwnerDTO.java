package com.tarakki.organization.dto;

import com.tarakki.common.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationOwnerDTO {
    private UUID memberId;
    private String firstName;
    private String lastName;
    private String email;
    private String profilePhotoS3Key;
    private AccountStatus accountStatus;
}
