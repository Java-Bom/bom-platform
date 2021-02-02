package com.javabom.bomplatform.core.user.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String githubId;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @CreatedDate
    private LocalDateTime createDate;

}

