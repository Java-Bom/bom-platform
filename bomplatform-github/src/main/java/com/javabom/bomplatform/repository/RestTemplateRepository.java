package com.javabom.bomplatform.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Repository
public class RestTemplateRepository<T> {

    // https://www.baeldung.com/rest-template
    private final RestTemplate restTemplate;

    public RestTemplateRepository(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<T> call(URI uri, HttpEntity<T> entity, HttpMethod httpMethod, Class<T> object) {
        return restTemplate.exchange(uri, httpMethod, entity, object);
    }

}
