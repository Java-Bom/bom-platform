package com.javabom.bomplatform.slack.model;

import com.javabom.bomplatform.slack.model.response.SlackUserResponse;
import com.javabom.bomplatform.slack.property.UserFinderProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SlackUserFinder {

    private final UserFinderProperty slackUserProperty;
    private final RestTemplateBuilder slackRestTemplate;

    public String findUserIdByEmail(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", slackUserProperty.getToken());
        map.add("email", email);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        final RestTemplate restTemplate = slackRestTemplate.build();
        ResponseEntity<SlackUserResponse> response = restTemplate.postForEntity(
                slackUserProperty.getUrl(), request, SlackUserResponse.class);
        final SlackUserResponse slackUserResponse = response.getBody();

        return Objects.requireNonNull(slackUserResponse).getUserId();
    }
}
