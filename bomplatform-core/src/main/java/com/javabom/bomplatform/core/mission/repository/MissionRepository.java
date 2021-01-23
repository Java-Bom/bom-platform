package com.javabom.bomplatform.core.mission.repository;

import com.javabom.bomplatform.core.mission.model.Mission;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends PagingAndSortingRepository<Mission, Long> {

}
