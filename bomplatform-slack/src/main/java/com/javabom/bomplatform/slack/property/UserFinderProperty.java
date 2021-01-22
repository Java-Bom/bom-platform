package com.javabom.bomplatform.slack.property;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("slack.user")
@Setter
public class UserFinderProperty {

    private String token;
    private String url;

    public String getToken() {
        return token;
    }

    public String getUrl() {
        return url;
    }
}
