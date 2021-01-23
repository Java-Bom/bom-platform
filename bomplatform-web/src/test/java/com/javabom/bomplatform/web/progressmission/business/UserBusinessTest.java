package com.javabom.bomplatform.web.progressmission.business;

import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.model.UserRole;
import com.javabom.bomplatform.core.user.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserBusinessTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBusiness userBusiness;

    @DisplayName("Challenger인 User를 찾는다.")
    @Test
    public void findChallengerById() throws Exception {
        //given
        final User challengerUser = User.builder()
                .email("testUser@test.com")
                .githubId("testUser")
                .userRole(UserRole.CHALLENGER)
                .build();

        userRepository.save(challengerUser);

        //when
        final User actualUser = userBusiness.findChallengerById(challengerUser.getId());

        //then
        assertThat(actualUser.getId()).isEqualTo(challengerUser.getId());
    }

    @DisplayName("찾은 User가 Challenger가 아니면 Exception을 발생시킨다.")
    @Test
    public void findChallengerByIdException() throws Exception {
        //given
        final User challengerUser = User.builder()
                .email("testUser@test.com")
                .githubId("testUser")
                .userRole(UserRole.REVIEWER)
                .build();

        userRepository.save(challengerUser);

        //then
        assertThatThrownBy(() -> userBusiness.findChallengerById(challengerUser.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("is not challenger");
    }
}
