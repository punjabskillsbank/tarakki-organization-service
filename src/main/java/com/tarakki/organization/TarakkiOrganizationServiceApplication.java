package com.tarakki.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import com.tarakki.common.config.CorsConfig;

@SpringBootApplication
@EntityScan(basePackages = "com.tarakki.common.entity")
@ComponentScan(
        basePackages = {"com.tarakki.organization", "com.tarakki.common"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CorsConfig.class)
)
public class TarakkiOrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TarakkiOrganizationServiceApplication.class, args);
    }

}
