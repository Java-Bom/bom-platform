package com.javabom.bomplatform.event.config;

import com.javabom.bomplatform.event.process.EventBrokerGroup;
import com.javabom.bomplatform.event.process.EventConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    public EventConsumer eventConsumer(EventBrokerGroup eventBrokerGroup) {
        return new EventConsumer(eventBrokerGroup);
    }

    @Bean
    public EventBrokerGroup eventBrokerGroup() {
        return new EventBrokerGroup();
    }

}
