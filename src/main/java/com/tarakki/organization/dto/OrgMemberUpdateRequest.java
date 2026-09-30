package com.tarakki.organization.dto;

import com.tarakki.organization.enums.MemberAccountStatus;
import com.tarakki.organization.enums.OrgMemberRole;
import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrgMemberUpdateRequest {
    private MemberAccountStatus memberAccountStatus;

    private OrgMemberRole orgMemberRole;
}
