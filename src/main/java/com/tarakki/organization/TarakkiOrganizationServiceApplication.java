package com.tarakki.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

@SpringBootApplication(scanBasePackages = {"com.tarakki.organization", "com.tarakki.common"})
@EntityScan(basePackages = "com.tarakki.common.entity")
public class TarakkiOrganizationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TarakkiOrganizationServiceApplication.class, args);
    }

    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }

}
