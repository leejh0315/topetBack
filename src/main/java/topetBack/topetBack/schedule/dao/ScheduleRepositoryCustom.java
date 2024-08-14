package topetBack.topetBack.schedule.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import topetBack.topetBack.schedule.domain.ScheduleResponseDTO;

@Repository
public interface ScheduleRepositoryCustom {
	List<ScheduleResponseDTO> getTodaySchedules(Long animalId);
}
