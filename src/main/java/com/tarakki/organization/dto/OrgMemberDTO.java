package com.tarakki.organization.dto;

import com.tarakki.organization.enums.MemberAccountStatus;
import com.tarakki.organization.enums.OrgMemberRole;
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

    private Long orgId;

    private UUID memberId;

    private MemberAccountStatus memberAccountStatus;

    private OrgMemberRole orgMemberRole;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}