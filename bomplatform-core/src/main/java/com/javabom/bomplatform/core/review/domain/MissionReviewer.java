package com.javabom.bomplatform.core.review.domain;

import com.javabom.bomplatform.core.mission.domain.ProgressMission;
import com.javabom.bomplatform.core.user.domain.User;

import javax.persistence.*;

@Entity
public class MissionReviewer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private ProgressMission progressMission;
}
