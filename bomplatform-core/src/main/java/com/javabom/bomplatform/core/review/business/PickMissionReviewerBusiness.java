package com.javabom.bomplatform.core.review.business;

import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PickMissionReviewerBusiness {

    public MissionReviewer pickMissionReviewer() {
        return MissionReviewer.builder().build();
    }

    public List<MissionReviewer> pickMissionReviewers(int size) {
        return new ArrayList<>();
    }

}
