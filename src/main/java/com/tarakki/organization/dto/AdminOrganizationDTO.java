package com.tarakki.organization.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminOrganizationDTO {
    private Long orgId;
    private String orgName;
    private String orgDesc;
    private OrganizationOwnerDTO owner;
    private String orgAddress;
    private String orgCity;
    private String orgState;
    private String orgPostalCode;
    private String orgCountry;
    private Long totalMemberCount;
}
