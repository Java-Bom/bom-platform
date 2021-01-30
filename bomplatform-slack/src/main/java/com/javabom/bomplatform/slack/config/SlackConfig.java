package com.javabom.bomplatform.slack.config;

import com.javabom.bomplatform.event.config.EventConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.javabom.bomplatform.slack"})
@Import(EventConfig.class)
public class SlackConfig {
}
