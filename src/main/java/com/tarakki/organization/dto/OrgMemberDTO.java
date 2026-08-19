package com.tarakki.organization.dto;

import com.tarakki.organization.enums.MemberAccountStatus;
import com.tarakki.organization.enums.OrgMemberRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgMemberDTO {
    private Long orgMemberId;

    @NotNull(message = "OrgId must not be empty")
    private Long orgId;

    private UUID memberId;

    @NotBlank(message = "email must not be empty")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull(message = "MemberAccountStatus must not be empty")
    private MemberAccountStatus memberAccountStatus = MemberAccountStatus.PENDING;

    @NotNull(message = "OrgMemberRole must not be empty")
    private OrgMemberRole orgMemberRole;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
