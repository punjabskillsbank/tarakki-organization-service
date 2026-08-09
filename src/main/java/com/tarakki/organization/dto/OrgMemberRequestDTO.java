package com.tarakki.organization.dto;

import com.tarakki.organization.enums.MemberAccountStatus;
import com.tarakki.organization.enums.OrgMemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgMemberRequestDTO {

    @NotBlank(message = "email must not be empty")
    @Email(message = "Please enter a valid email address")
    private String email;

    private MemberAccountStatus memberAccountStatus;

    @NotNull(message = "OrgMemberRole must not be empty")
    private OrgMemberRole orgMemberRole;
}
