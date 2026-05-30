package com.tarakki.organization.repository;

import com.tarakki.common.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    @Query(value = """
            SELECT
                o.org_id AS "orgId",
                o.org_name AS "orgName",
                o.org_desc AS "orgDesc",
                o.owner_id AS "ownerId",
                o.org_address AS "orgAddress",
                o.org_city AS "orgCity",
                o.org_state AS "orgState",
                o.org_postal_code AS "orgPostalCode",
                o.org_country AS "orgCountry",
                COUNT(DISTINCT om.member_id)
                    + CASE WHEN COUNT(DISTINCT owner_member.member_id) = 0 THEN 1 ELSE 0 END AS "totalMemberCount"
            FROM organizations o
            LEFT JOIN org_members om ON om.org_id = o.org_id
            LEFT JOIN org_members owner_member
                ON owner_member.org_id = o.org_id
                AND owner_member.member_id = o.owner_id
            GROUP BY
                o.org_id,
                o.org_name,
                o.org_desc,
                o.owner_id,
                o.org_address,
                o.org_city,
                o.org_state,
                o.org_postal_code,
                o.org_country
            ORDER BY o.org_id
            """, nativeQuery = true)
    List<Map<String, Object>> findAllOrganizationsWithOwnerAndMemberCount();
}
