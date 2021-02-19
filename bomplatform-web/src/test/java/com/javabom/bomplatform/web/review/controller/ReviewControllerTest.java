package com.javabom.bomplatform.web.review.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.progressmission.model.ProgressMissionState;
import com.javabom.bomplatform.core.progressmission.repository.ProgressMissionRepository;
import com.javabom.bomplatform.core.review.repository.ReviewRepository;
import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.model.UserRole;
import com.javabom.bomplatform.core.user.repository.UserRepository;
import com.javabom.bomplatform.web.config.WebConfig;
import com.javabom.bomplatform.web.review.controller.dto.response.ReviewDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class ReviewControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgressMissionRepository progressMissionRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private User challenger;
    private User reviewer;
    private User watcher;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        init();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        progressMissionRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @DisplayName("Challenger githubId로 review list를 조회한다.")
    @ParameterizedTest
    @CsvSource({"challenger, 2, url1", "reviewer, 1, subUrl"})
    void getChallengerReviewsTest(String githubId, int size, String reviewUrl) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/v1/reviews/challenger/" + githubId)
                .param("pageNo", "0")
                .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();

        List<ReviewDto> results = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ReviewDto>>() {
        });

        assertAll(
                () -> assertThat(results.size()).isEqualTo(size),
                () -> assertThat(results.get(0).getReviewUrl()).isEqualTo(reviewUrl)
        );
    }

    private void init() {
        saveUsers();

        ProgressMission progressMission = ProgressMission.builder()
                .progressMissionState(ProgressMissionState.OPEN)
                .challenger(challenger)
                .missionReviewers(Arrays.asList(toMissionReviewer(reviewer)))
                .build();

        ProgressMission progressMission2 = ProgressMission.builder()
                .progressMissionState(ProgressMissionState.OPEN)
                .challenger(reviewer)
                .missionReviewers(Arrays.asList(toMissionReviewer(challenger)))
                .build();

        progressMissionRepository.save(progressMission);
        reviewRepository.save(progressMission.createReview("url1"));
        reviewRepository.save(progressMission.createReview("url2"));

        progressMissionRepository.save(progressMission2);
        reviewRepository.save(progressMission2.createReview("subUrl"));
    }

    private void saveUsers() {
        challenger = userRepository.save(new User("challenger@gmail.com", "challenger", UserRole.CHALLENGER));
        reviewer = userRepository.save(new User("reviewer@gmail.com", "reviewer", UserRole.REVIEWER));
        watcher = userRepository.save(new User("watcher@gmail.com", "watcher", UserRole.CHALLENGER));
    }

    private MissionReviewer toMissionReviewer(User user) {
        return new MissionReviewer(user.getEmail(), user.getGithubId());
    }
}