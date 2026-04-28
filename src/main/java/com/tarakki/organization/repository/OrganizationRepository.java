package com.tarakki.organization.repository;

import com.tarakki.organization.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}
