package com.javabom.bomplatform.slack.property;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "slack.message")
public class MessageSenderProperty {

    private final String webHookUrl;

    public String getWebHookUrl() {
        return this.webHookUrl;
    }
}
