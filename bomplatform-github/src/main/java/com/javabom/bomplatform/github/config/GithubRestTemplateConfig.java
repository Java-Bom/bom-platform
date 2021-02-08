package com.javabom.bomplatform.github.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class GithubRestTemplateConfig {

    private static final int READ_TIME = 3000;
    private static final int CONNECT_TIME = 3000;

    @Bean
    public RestTemplateBuilder githubRestTemplateBuilder() {
        RestTemplateBuilder builder = new RestTemplateBuilder();

        return builder.setReadTimeout(Duration.ofMillis(READ_TIME))
                .setConnectTimeout(Duration.ofMillis(CONNECT_TIME));
    }
}
