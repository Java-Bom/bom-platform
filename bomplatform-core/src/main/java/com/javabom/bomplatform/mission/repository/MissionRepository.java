package com.javabom.bomplatform.mission.repository;

import com.javabom.bomplatform.mission.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
}
