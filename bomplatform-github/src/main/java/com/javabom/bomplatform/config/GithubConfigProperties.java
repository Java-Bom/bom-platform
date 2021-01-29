package com.javabom.bomplatform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.util.Base64;

@Configuration
@ConfigurationProperties(prefix = "github")
public class GithubConfigProperties {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
