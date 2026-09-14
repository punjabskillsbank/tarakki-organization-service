package com.tarakki.organization.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.tarakki.common.entity", "com.tarakki.organization.entity"})
@EnableJpaRepositories(basePackages = {"com.tarakki.organization.repository"})
public class JpaConfig {
}
