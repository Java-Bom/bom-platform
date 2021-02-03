package com.javabom.bomplatform.core.progressmission.business;

import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserBusiness {

    private final UserRepository userRepository;

    public User findChallengerById(Long userId) {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException(String.format("input id: %d, not found user", userId)));

        if(user.isNotChallenger()){
            throw new IllegalArgumentException("is not challenger");
        }

        return user;
    }
}
