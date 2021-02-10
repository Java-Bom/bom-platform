package com.javabom.bomplatform.github.property;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "github")
public class GithubProperties {

    private final String token;

    public String getToken() {
        return token;
    }
}
