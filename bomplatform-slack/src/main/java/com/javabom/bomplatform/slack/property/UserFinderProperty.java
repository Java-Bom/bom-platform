package com.javabom.bomplatform.slack.property;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "slack.user")
public class UserFinderProperty {

    private final String token;
    private final String url;

    public String getToken() {
        return token;
    }

    public String getUrl() {
        return url;
    }
}
