package com.javabom.bomplatform.core.progressmission.business;

import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PickMissionReviewerBusiness {
    MissionReviewer pickMissionReviewer();

    List<MissionReviewer> pickMissionReviewers(int size);
}