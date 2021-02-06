package com.javabom.bomplatform.core.user.business;

import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.model.UserRole;
import com.javabom.bomplatform.core.user.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserBusinessTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserBusiness userBusiness;

    @DisplayName("Challenger인 User를 찾는다.")
    @Test
    public void findChallengerByIdTest() throws Exception {
        //given
        final User saveUser = saveUser(UserRole.CHALLENGER);

        //when
        final User findUser = userBusiness.findChallengerById(saveUser.getId());

        //then
        assertThat(findUser.getId()).isEqualTo(saveUser.getId());
    }

    @DisplayName("등록되지 않은 User 조회 시 IllegalArgumentException을 발생시킨다.")
    @Test
    public void findChallengerByIdExceptionTest1() throws Exception {
        //given
        final User saveUser = saveUser(UserRole.CHALLENGER);
        final Long notSaveUserId = saveUser.getId() + 1;

        //then
        assertThatThrownBy(() -> userBusiness.findChallengerById(notSaveUserId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("input id: %d, not found user", notSaveUserId));
    }

    @DisplayName("찾은 User가 Challenger가 아니면 IllegalArgumentException을 발생시킨다.")
    @Test
    public void findChallengerByIdExceptionTest2() throws Exception {
        //given
        final User saveUser = saveUser(UserRole.REVIEWER);

        //then
        assertThatThrownBy(() -> userBusiness.findChallengerById(saveUser.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("is not challenger");
    }

    private User saveUser(UserRole userRole) {
        final User user = User.builder()
                .email("testUser@test.com")
                .githubId("testUser")
                .userRole(userRole)
                .build();

        userRepository.save(user);
        return user;
    }
}
