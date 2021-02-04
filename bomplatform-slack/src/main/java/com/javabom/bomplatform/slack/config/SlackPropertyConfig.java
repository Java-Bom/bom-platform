package com.javabom.bomplatform.slack.config;

import com.javabom.bomplatform.slack.property.MessageSenderProperty;
import com.javabom.bomplatform.slack.property.UserFinderProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {MessageSenderProperty.class, UserFinderProperty.class})
public class SlackPropertyConfig {
}
