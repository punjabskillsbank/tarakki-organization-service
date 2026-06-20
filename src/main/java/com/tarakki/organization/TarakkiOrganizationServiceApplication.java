package com.tarakki.organization;

import com.tarakki.common.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = {"com.tarakki.organization", "com.tarakki.common"})
@EntityScan(basePackages = "com.tarakki.common.entity")
@Import(CorsConfig.class)
public class TarakkiOrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TarakkiOrganizationServiceApplication.class, args);
    }
}
