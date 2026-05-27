package com.tarakki.organization.repository;

import com.tarakki.common.entity.Organization;
import com.tarakki.organization.repository.projection.AdminOrganizationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query(value = """
            SELECT
                o.org_id AS "orgId",
                o.org_name AS "orgName",
                o.org_desc AS "orgDesc",
                o.owner_id AS "ownerId",
                m.first_name AS "ownerFirstName",
                m.last_name AS "ownerLastName",
                m.email AS "ownerEmail",
                m.profile_photo_s3_key AS "ownerProfilePhotoS3Key",
                CAST(m.account_status AS text) AS "ownerAccountStatus",
                o.org_address AS "orgAddress",
                o.org_city AS "orgCity",
                o.org_state AS "orgState",
                o.org_postal_code AS "orgPostalCode",
                o.org_country AS "orgCountry",
                COUNT(om.member_id) AS "totalMemberCount"
            FROM organizations o
            JOIN members m ON m.member_id = o.owner_id
            LEFT JOIN org_members om ON om.org_id = o.org_id
            GROUP BY
                o.org_id,
                o.org_name,
                o.org_desc,
                o.owner_id,
                m.first_name,
                m.last_name,
                m.email,
                m.profile_photo_s3_key,
                m.account_status,
                o.org_address,
                o.org_city,
                o.org_state,
                o.org_postal_code,
                o.org_country
            ORDER BY o.org_id
            """, nativeQuery = true)
    List<AdminOrganizationProjection> findAllOrganizationsWithOwnerAndMemberCount();
}
