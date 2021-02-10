package com.javabom.bomplatform.github.config;

import com.javabom.bomplatform.github.property.GithubProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GithubProperties.class)
public class GithubPropertiesConfig {
}
