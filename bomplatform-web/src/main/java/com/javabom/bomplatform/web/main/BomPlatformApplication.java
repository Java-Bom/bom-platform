package com.javabom.bomplatform.web.main;

import com.javabom.bomplatform.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(WebConfig.class)
public class BomPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(BomPlatformApplication.class, args);
    }
}