package com.javabom.bomplatform.github.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Repository
public class RestTemplateRepository<T> {

    private final RestTemplateBuilder githubRestTemplateBuilder;

    public RestTemplateRepository(@Qualifier("githubRestTemplateBuilder") RestTemplateBuilder githubRestTemplateBuilder) {
        this.githubRestTemplateBuilder = githubRestTemplateBuilder;
    }

    public ResponseEntity<T> call(URI uri, HttpEntity<T> entity, HttpMethod httpMethod, Class<T> object) {
        final RestTemplate restTemplate = githubRestTemplateBuilder.build();
        return restTemplate.exchange(uri, httpMethod, entity, object);
    }
}
