package com.javabom.bomplatform.github.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class SlackRestTemplateConfig {

    @Bean
    public RestTemplateBuilder slackRestTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder();

        return builder.setReadTimeout(Duration.ofMillis(3000))
                .setConnectTimeout(Duration.ofMillis(3000));
    }
}
