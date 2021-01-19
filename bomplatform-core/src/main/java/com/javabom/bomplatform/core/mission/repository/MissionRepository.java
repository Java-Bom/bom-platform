package com.javabom.bomplatform.core.mission.repository;

import com.javabom.bomplatform.core.mission.model.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
}
