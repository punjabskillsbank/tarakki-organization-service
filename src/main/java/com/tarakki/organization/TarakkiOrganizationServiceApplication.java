package com.tarakki.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.tarakki.common.entity")
public class TarakkiOrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TarakkiOrganizationServiceApplication.class, args);
    }

}
