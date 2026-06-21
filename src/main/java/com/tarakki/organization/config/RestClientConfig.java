package com.tarakki.organization.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public RestClient restClient(
            RestClient.Builder builder,
            @Value("${member.service.base-url}") String memberServiceBaseUrl
    ) {
        return builder
                .baseUrl(memberServiceBaseUrl)
                .build();
    }
}