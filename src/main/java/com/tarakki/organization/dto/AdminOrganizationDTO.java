package com.tarakki.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.tarakki.common.entity.Member;

@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrganizationDTO {
    private Long orgId;
    private String orgName;
    private String orgDesc;
    private Member owner;
    private String orgAddress;
    private String orgCity;
    private String orgState;
    private String orgPostalCode;
    private String orgCountry;
    private Long totalMemberCount;
}
