package com.javabom.bomplatform.user.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    private String email;
    private String githubId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public boolean isNotChallenger() {
        return !isChallenger();
    }

    public boolean isChallenger() {
        return this.userRole == UserRole.CHALLENGER;
    }
}

