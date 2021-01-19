package com.javabom.bomplatform.core.progressmission.repository;

import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgressMissionRepository extends JpaRepository<ProgressMission, Long> {
}
