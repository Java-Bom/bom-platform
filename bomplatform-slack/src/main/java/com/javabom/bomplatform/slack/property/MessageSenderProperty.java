package com.javabom.bomplatform.slack.property;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "slack.message")
@Setter
public class MessageSenderProperty {

    private String webHookUrl;

    public String getWebHookUrl() {
        return this.webHookUrl;
    }
}
