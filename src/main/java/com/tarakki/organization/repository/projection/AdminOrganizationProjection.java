package com.tarakki.organization.repository.projection;

import java.util.UUID;

public interface AdminOrganizationProjection {
    Long getOrgId();
    String getOrgName();
    String getOrgDesc();
    UUID getOwnerId();
    String getOwnerFirstName();
    String getOwnerLastName();
    String getOwnerEmail();
    String getOwnerProfilePhotoS3Key();
    String getOwnerAccountStatus();
    String getOrgAddress();
    String getOrgCity();
    String getOrgState();
    String getOrgPostalCode();
    String getOrgCountry();
    Long getTotalMemberCount();
}
