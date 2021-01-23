package com.javabom.bomplatform.web.progressmission.business;

import com.javabom.bomplatform.progressmission.model.MissionReviewer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PickMissionReviewerBusiness {
    MissionReviewer pickMissionReviewer();

    List<MissionReviewer> pickMissionReviewers(int size);
}