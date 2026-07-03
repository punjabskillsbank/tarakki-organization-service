package com.tarakki.organization.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient.Builder restClientBuilder(
            @Value("${restclient.connect-timeout-ms:3000}") int connectTimeout,
            @Value("${restclient.read-timeout-ms:5000}") int readTimeout) {
        
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectTimeout));
        factory.setReadTimeout(Duration.ofMillis(readTimeout));
        
        return RestClient.builder().requestFactory(factory);
    }

    @Bean
    public RestClient memberServiceRestClient(
            RestClient.Builder builder,
            @Value("${member.service.base-url}") String memberServiceBaseUrl
    ) {
        return builder
                .baseUrl(memberServiceBaseUrl)
                .build();
    }
}
